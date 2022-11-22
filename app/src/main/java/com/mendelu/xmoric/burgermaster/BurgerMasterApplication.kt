package com.mendelu.xmoric.burgermaster

import android.app.Application
import android.content.Context
import com.mendelu.xmoric.burgermaster.dependency.*
import org.koin.android.BuildConfig

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Main application class.
 * Initializes Koin DI.

 */
class BurgerMasterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@BurgerMasterApplication)
            modules(listOf(
                databaseModule,
                daoModule,
                repositoryModule,
                viewModelModule,
                dataStoreModule
            ))
        }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }

}