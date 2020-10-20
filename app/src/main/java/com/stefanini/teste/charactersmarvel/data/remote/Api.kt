package com.stefanini.teste.charactersmarvel.data.remote

import com.stefanini.teste.charactersmarvel.data.remote.dto.BaseMarvel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Carlos Souza on 18,October,2020
 */
interface Api {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?,
    ): BaseMarvel
}