package com.stefanini.teste.charactersmarvel.adapter

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.character_item.view.*

/**
 * Created by Carlos Souza on 19,October,2020
 */
class CharacterHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var image: ImageView = itemView.imgCharacter
    var name: TextView = itemView.txtCharacterName
    var card: LinearLayout = itemView.llCharacterItem
}