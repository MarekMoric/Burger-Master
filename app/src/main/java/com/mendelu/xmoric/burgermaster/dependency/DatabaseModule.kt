package com.mendelu.xmoric.burgermaster.dependency

import com.mendelu.xmoric.burgermaster.BurgerMasterApplication
import com.mendelu.xmoric.burgermaster.database.BurgerDatabase
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(): BurgerDatabase = BurgerDatabase.getDatabase(BurgerMasterApplication.appContext)
    single {
        provideDatabase()
    }
}