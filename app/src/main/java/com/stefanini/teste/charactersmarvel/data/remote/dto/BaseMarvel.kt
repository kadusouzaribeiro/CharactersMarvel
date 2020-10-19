package com.stefanini.teste.charactersmarvel.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseMarvel (
	val code : Int,
	val status : String,
	val copyright : String,
	val attributionText : String,
	val attributionHTML : String,
	val etag : String,
	val data : Data
)