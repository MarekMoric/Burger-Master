package com.mendelu.xmoric.burgermaster.dependency

import com.mendelu.xmoric.burgermaster.database.BurgersDao
import com.mendelu.xmoric.burgermaster.database.BurgersLocalRepositoryImpl
import com.mendelu.xmoric.burgermaster.database.IBurgerLocalRepository
import org.koin.dsl.module

val repositoryModule = module {
    fun provideLocalBurgerRepository(dao: BurgersDao): IBurgerLocalRepository {
        return BurgersLocalRepositoryImpl(dao)
    }
    single { provideLocalBurgerRepository(get()) }
}