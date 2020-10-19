package com.stefanini.teste.charactersmarvel

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Window
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stefanini.teste.charactersmarvel.data.local.CharacterEntity
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.character_detail.*

/**
 * Created by Carlos Souza on 19,October,2020
 */
class CharacterDetailDialog() : DaggerDialogFragment() {

    companion object {
        fun newInstance() = CharacterDetailDialog()
    }

    private lateinit var character: CharacterEntity

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialog = super.onCreateDialog(savedInstanceState)

        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.character_detail)

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_POWER) {
                val closeDialog = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
                this.requireActivity().sendBroadcast(closeDialog)
            }
            false
        }

        GlideApp.with(this)
            .load(character.image)
            .placeholder(R.drawable.ic_placeholder_foreground)
            .error(R.drawable.without_image)
            .fallback(R.drawable.without_image)
            .into(dialog.imgDetailImage)

        dialog.txtDetailName.text = character.name
        dialog.txtDetailDesc.text = character.description
        dialog.txtDetailComics.text = String.format("%s %02d", getString(R.string.comics), character.comics)
        dialog.txtDetailStories.text = String.format("%s %02d", getString(R.string.stories), character.stories)
        dialog.txtDetailSeries.text = String.format("%s %02d", getString(R.string.series), character.series)
        dialog.txtDetailEvents.text = String.format("%s %02d", getString(R.string.events), character.events)

        dialog.btDetailClose.setOnClickListener { this.dismissAllowingStateLoss() }

        return dialog
    }

    fun setChar(characterEntity: CharacterEntity) {
        character = characterEntity
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (ex: Exception) {
            Log.e("Detail", "show", ex)
        }
    }
}