package com.stefanini.teste.charactersmarvel

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
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
import com.stefanini.teste.charactersmarvel.databinding.ActivityMainBinding
import com.stefanini.teste.charactersmarvel.util.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import java.text.SimpleDateFormat
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    private val TAG = "MainActivity"

    lateinit var mainViewModel: MainViewModel

    lateinit var pbLoading: ProgressBar

    lateinit var txtLoading: TextView

    lateinit var rvCharacters: RecyclerView

    lateinit var charactersAdapter: CharacterAdapter

    var loading = false

    var listCharacters = mutableListOf<CharacterEntity>()

    var offset = 0

    var limit = 50

    lateinit var cDialog: AlertDialog

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBinding()

        mainViewModel = ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)

        Log.d(TAG, "onCreate: getcharacter")

        mainViewModel.getCharacters(offset, limit)
        
        mainViewModel.characters.removeObservers(this)
        mainViewModel.characters.observe(this, {
            when (it.status) {
                ResponseStatus.LOADING -> {
                    Log.d(TAG, "onCreate: Consultando API")
                    txtLoading.text = "Buscando Informações"
                }
                ResponseStatus.ERROR -> {
                    pbLoading.visibility = View.GONE
                    //llProgress.visibility = View.GONE
                    txtLoading.text = it.message
                }
                ResponseStatus.SUCCESS -> {
                    pbLoading.visibility = View.GONE
                    if (loading) {
                        cDialog.dismiss()
                    }
                    txtLoading.visibility = View.GONE
                    it.data?.forEach { it ->
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
        })
    }

    fun setBinding() {
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        pbLoading = binding.pbLoading
        txtLoading = binding.txtLoading
        rvCharacters = binding.rvCharacters
    }

    fun setListCharacter() {
        charactersAdapter = CharacterAdapter(listCharacters, this)

        var llManager = LinearLayoutManager(this)
        rvCharacters.layoutManager = llManager
        rvCharacters.adapter = charactersAdapter
        rvCharacters.visibility = View.VISIBLE

        rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    fun progress() {
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
}