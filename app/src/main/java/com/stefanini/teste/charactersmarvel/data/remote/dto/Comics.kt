package com.stefanini.teste.charactersmarvel.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comics (

	val available : Int,
	val collectionURI : String,
	val items : List<Items>,
	val returned : Int
)