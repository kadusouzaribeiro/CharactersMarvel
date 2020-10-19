package com.stefanini.teste.charactersmarvel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stefanini.teste.charactersmarvel.*
import com.stefanini.teste.charactersmarvel.data.local.CharacterEntity
import javax.inject.Inject

/**
 * Created by Carlos Souza on 19,October,2020
 */
class CharacterAdapter @Inject constructor (var characterList: List<CharacterEntity>, private val activity: MainActivity): RecyclerView.Adapter<CharacterHolder>() {

    var characterDetailDialog = CharacterDetailDialog()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        return CharacterHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.character_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val character = characterList[position]
        GlideApp.with(activity)
            .load(character.image)
            .placeholder(R.drawable.ic_placeholder_foreground)
            .error(R.drawable.without_image)
            .fallback(R.drawable.without_image)
            .into(holder.image)
        holder.name.text = character.name
        holder.card.setOnClickListener {
            Log.d("Adapter", "onBindViewHolder: $character")
            characterDetailDialog.setChar(character)
            characterDetailDialog.show(activity.supportFragmentManager, "characterDetailDialog")
        }
    }

    override fun getItemCount(): Int {
        return characterList.size
    }
}