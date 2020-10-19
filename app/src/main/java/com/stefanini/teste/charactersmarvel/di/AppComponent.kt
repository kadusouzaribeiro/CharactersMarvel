package com.stefanini.teste.charactersmarvel.di

import com.stefanini.teste.charactersmarvel.MainApplication
import com.stefanini.teste.charactersmarvel.data.remote.ApiRepository
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Carlos Souza on 18,October,2020
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        AppModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent: AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun mainApplication(mainApplication: MainApplication): Builder

        fun build(): AppComponent
    }
}