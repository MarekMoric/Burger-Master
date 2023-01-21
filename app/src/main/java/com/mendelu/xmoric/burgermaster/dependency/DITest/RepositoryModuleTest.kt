package com.mendelu.xmoric.burgermaster.dependency.DITest

import com.mendelu.xmoric.burgermaster.database.BurgersLocalRepositoryImplTest
import com.mendelu.xmoric.burgermaster.database.IBurgerLocalRepository
import org.koin.dsl.module

val RepositoryModuleTest = module {

    single { provideLocalBurgerRepositoryTest() }
}

fun provideLocalBurgerRepositoryTest(): IBurgerLocalRepository {
    return BurgersLocalRepositoryImplTest()
}