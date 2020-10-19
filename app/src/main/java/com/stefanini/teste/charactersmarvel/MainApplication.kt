package com.stefanini.teste.charactersmarvel

import com.stefanini.teste.charactersmarvel.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by Carlos Souza on 18,October,2020
 */
open class MainApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().mainApplication(this).build()
    }
}