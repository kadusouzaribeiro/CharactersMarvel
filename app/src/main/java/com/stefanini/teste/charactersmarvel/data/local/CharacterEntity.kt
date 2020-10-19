package com.stefanini.teste.charactersmarvel.data.local

/**
 * Created by Carlos Souza on 19,October,2020
 */
data class CharacterEntity (
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val comics: Int,
    val stories: Int,
    val events: Int,
    val series: Int,
    val lastUpdate: String
)