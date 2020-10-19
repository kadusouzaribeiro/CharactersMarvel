package com.stefanini.teste.charactersmarvel.di

import androidx.lifecycle.ViewModel
import com.stefanini.teste.charactersmarvel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Carlos Souza on 18,October,2020
 */
@Module
abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}