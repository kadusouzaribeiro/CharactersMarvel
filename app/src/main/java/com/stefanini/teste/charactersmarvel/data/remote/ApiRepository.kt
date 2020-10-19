package com.stefanini.teste.charactersmarvel.data.remote

import javax.inject.Inject

/**
 * Created by Carlos Souza on 18,October,2020
 */
class ApiRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getCharacters(os: Int?, limit: Int?) = api.getCharacters(os, limit)
}