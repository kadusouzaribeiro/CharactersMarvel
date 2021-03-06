package com.stefanini.teste.charactersmarvel.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Results (

	val id : Int,
	val name : String,
	val description : String,
	val modified : String,
	val thumbnail : Thumbnail,
	val resourceURI : String,
	val comics : Comics,
	val series : Series,
	val stories : Stories,
	val events : Events,
	val urls : MutableList<Urls>
)