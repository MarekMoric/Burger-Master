package com.mendelu.xmoric.burgermaster

import android.app.Application
import android.content.Context
import com.mendelu.xmoric.burgermaster.dependency.DITest.RepositoryModuleTest
import com.mendelu.xmoric.burgermaster.dependency.*
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BurgerMasterApplicationTest : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@BurgerMasterApplicationTest)
            modules(listOf(
                    RepositoryModuleTest, viewModelModule, databaseModule
                ))
        }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }

}