package com.mendelu.xmoric.burgermaster.dependency

import com.mendelu.xmoric.burgermaster.database.BurgerDatabase
import com.mendelu.xmoric.burgermaster.database.BurgersDao
import org.koin.dsl.module

val daoModule = module {
    fun provideBurgersDao(database: BurgerDatabase): BurgersDao = database.burgersDao()
    single {
        provideBurgersDao(get())
    }
}