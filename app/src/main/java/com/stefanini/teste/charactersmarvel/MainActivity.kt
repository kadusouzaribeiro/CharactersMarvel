package com.stefanini.teste.charactersmarvel

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stefanini.teste.charactersmarvel.adapter.CharacterAdapter
import com.stefanini.teste.charactersmarvel.data.local.CharacterEntity
import com.stefanini.teste.charactersmarvel.data.remote.ResponseStatus
import com.stefanini.teste.charactersmarvel.data.remote.dto.Results
import com.stefanini.teste.charactersmarvel.databinding.ActivityMainBinding
import com.stefanini.teste.charactersmarvel.util.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import java.text.SimpleDateFormat
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    private val TAG = "MainActivity"

    lateinit var mainViewModel: MainViewModel

    lateinit var charactersAdapter: CharacterAdapter

    private var loading = false

    private var listCharacters = mutableListOf<CharacterEntity>()

    private var offset = 0

    private var limit = 50

    lateinit var cDialog: AlertDialog

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )

        Log.d(TAG, "onCreate: getcharacter")

        mainViewModel.getCharacters(offset, limit)

        subscribeObserver()
    }

    private fun subscribeObserver() {
        mainViewModel.characters.removeObservers(this)
        mainViewModel.characters.observe(this, {
            when (it.status) {
                ResponseStatus.LOADING -> {
                    onResponseLoading()
                }
                ResponseStatus.ERROR -> {
                    onResponseError(it.message)
                }
                ResponseStatus.SUCCESS -> {
                    onResponseSuccess(it.data)
                }
            }
        })
    }

    private fun setListCharacter() {
        charactersAdapter = CharacterAdapter(listCharacters, this)

        var llManager = LinearLayoutManager(this)
        binding.rvCharacters.layoutManager = llManager
        binding.rvCharacters.adapter = charactersAdapter
        binding.rvCharacters.visibility = View.VISIBLE

        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItem: Int = llManager.itemCount
                val lastVisibleItem: Int = llManager.findLastVisibleItemPosition()
                Log.d(TAG, "onScrolled: totalItem $totalItem")
                Log.d(TAG, "onScrolled: lastVisibleItem $lastVisibleItem")
                if (!loading && lastVisibleItem == totalItem - 1) {
                    loading = true
                    offset += limit
                    progress()
                    mainViewModel.getCharacters(offset, limit)
                }
            }
        })
    }

    private fun progress() {
        cDialog = AlertDialog.Builder(this)
            .setView(layoutInflater.inflate(R.layout.dialog_loading, null))
            .setCancelable(false)
            .setOnKeyListener { dialog, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_POWER) {
                    val closeDialog = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
                    sendBroadcast(closeDialog)
                }
                false
            }
            .create()
        cDialog.show()
    }

    private fun onResponseLoading() {
        binding.txtLoading.text = String.format("%s", getString(R.string.getting_characters))
    }

    private fun onResponseError(msg: String?) {
        binding.pbLoading.visibility = View.GONE
        binding.txtLoading.text = msg
    }

    private fun onResponseSuccess(characters: List<Results>?) {
        binding.pbLoading.visibility = View.GONE
        binding.txtLoading.visibility = View.GONE
        if (loading) {
            cDialog.dismiss()
        }

        characters?.forEach { it ->
            val formatOrigin = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-SSSS")
            val formatTarget = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val lastUpdate = formatTarget.format(formatOrigin.parse(it.modified))
            var imageUrl =
                    "${it.thumbnail.path}//portrait_uncanny.${it.thumbnail.extension}"
            GlideApp.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_placeholder_foreground)
                    .error(R.drawable.without_image)
                    .fallback(R.drawable.without_image)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .preload()
            listCharacters.add(
                    CharacterEntity(
                            id = it.id,
                            name = it.name,
                            image = imageUrl,
                            description = it.description,
                            comics = it.comics.items.size,
                            stories = it.stories.items.size,
                            events = it.events.items.size,
                            series = it.series.items.size,
                            lastUpdate = lastUpdate
                    )
            )
        }
        if (loading) {
            charactersAdapter.notifyDataSetChanged()
            loading = false
        } else {
            setListCharacter()
        }
    }
}