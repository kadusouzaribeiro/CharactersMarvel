package com.stefanini.teste.charactersmarvel.di

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.stefanini.teste.charactersmarvel.BuildConfig
import com.stefanini.teste.charactersmarvel.MainApplication
import com.stefanini.teste.charactersmarvel.data.remote.Api
import com.stefanini.teste.charactersmarvel.util.headersInterceptor
import com.stefanini.teste.charactersmarvel.util.loggingInterceptor
import com.stefanini.teste.charactersmarvel.util.queryInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Carlos Souza on 18,October,2020
 */
@Module
class AppModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient().newBuilder()
        return clientBuilder
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(headersInterceptor())
            .addInterceptor(queryInterceptor())
            .addInterceptor(loggingInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(mainApplication: MainApplication, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build())
            ).build()
    }

    @Singleton
    @Provides
    fun provideSyncApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

}