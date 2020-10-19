package com.stefanini.teste.charactersmarvel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.stefanini.teste.charactersmarvel.data.remote.Api
import com.stefanini.teste.charactersmarvel.data.remote.ApiRepository
import com.stefanini.teste.charactersmarvel.data.remote.Resource
import com.stefanini.teste.charactersmarvel.data.remote.dto.BaseMarvel
import com.stefanini.teste.charactersmarvel.data.remote.dto.Results
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Scope

/**
 * Created by Carlos Souza on 18,October,2020
 */
class MainViewModel @Inject  constructor(
    private val apiRepository: ApiRepository
): ViewModel() {

    val characters = MutableLiveData<Resource<List<Results>>>()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        characters.postValue(Resource.error(null, exception.message ?: "Erro consultando API"))
    }

    fun getCharacters(offset: Int, limit: Int) = CoroutineScope(IO).launch(exceptionHandler) {
        characters.postValue(Resource.loading(null))
        apiRepository.getCharacters(offset, limit).let {
            if (it.code != 200) {
                characters.postValue(Resource.error(null, it.status))
            } else {
                characters.postValue(Resource.success(it.data.results))
            }
        }
    }
    //{
//        api.getCharacters(0, 50).enqueue(object : Callback<BaseMarvel> {
//            override fun onResponse(call: Call<BaseMarvel>, response: Response<BaseMarvel>) {
//                Log.d(TAG, "onResponse: ")
//            }
//
//            override fun onFailure(call: Call<BaseMarvel>, t: Throwable) {
//                Log.d(TAG, "onFailure: Erro ao buscar informações")
//            }
//
//        })

    //}
}