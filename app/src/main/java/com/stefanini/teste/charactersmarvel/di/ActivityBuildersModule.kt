package com.stefanini.teste.charactersmarvel.di

import com.stefanini.teste.charactersmarvel.CharacterDetailDialog
import com.stefanini.teste.charactersmarvel.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Carlos Souza on 18,October,2020
 */
@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [MainViewModelsModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeCharacterDetailDialog(): CharacterDetailDialog
}