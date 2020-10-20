package com.stefanini.teste.charactersmarvel.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data (

	val offset : Int,
	val limit : Int,
	val total : Int,
	val count : Int,
	val results : MutableList<Results>
)