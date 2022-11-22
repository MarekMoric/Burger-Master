package com.mendelu.xmoric.burgermaster.database

import kotlinx.coroutines.flow.Flow

class BurgersLocalRepositoryImpl (private val burgersDao: BurgersDao) : IBurgerLocalRepository {
    override fun getAll(): Flow<List<Burger>> {
        return burgersDao.getAll()
    }

    override suspend fun delete(burger: Burger) {
        burgersDao.delete(burger)
    }

    override suspend fun update(burger: Burger) {
        burgersDao.update(burger)
    }
}