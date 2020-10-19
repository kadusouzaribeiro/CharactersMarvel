package com.stefanini.teste.charactersmarvel.di

import androidx.lifecycle.ViewModelProvider
import com.stefanini.teste.charactersmarvel.util.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

/**
 * Created by Carlos Souza on 28,May,2020
 */
@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(modelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}