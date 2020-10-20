package com.stefanini.teste.charactersmarvel.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stories (

	val available : Int,
	val collectionURI : String,
	val items : MutableList<Items>,
	val returned : Int
)