package com.mendelu.xmoric.burgermaster.database

import com.mendelu.xmoric.burgermaster.database.Burger
import com.mendelu.xmoric.burgermaster.database.BurgersDao
import com.mendelu.xmoric.burgermaster.database.IBurgerLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BurgersLocalRepositoryImplTest : IBurgerLocalRepository {
    val burger = Burger("Test burger")
    val burger2 = Burger("Test burger 2")
    val burger3 = Burger("Test burger 3")
    val burgerList  = listOf(burger, burger2, burger3)


    override fun getAll(): Flow<List<Burger>> {
        val burger = Burger("Test burger")
        burger.id = 1
        val burger2 = Burger("Test burger 2")
        burger2.id = 2
        val burger3 = Burger("Test burger 3")
        burger3.id = 3
        val burgerList  = listOf(burger, burger2, burger3)
        return flowOf(burgerList)
    }

    override suspend fun findById(id: Long): Burger {
        return burger
    }

    override suspend fun insert(burger: Burger): Long {
        return 3
    }

    override suspend fun delete(burger: Burger) {

    }

    override suspend fun update(burger: Burger) {

    }
}
