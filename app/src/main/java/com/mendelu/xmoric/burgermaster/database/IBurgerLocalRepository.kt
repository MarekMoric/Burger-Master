package com.mendelu.xmoric.burgermaster.database

import kotlinx.coroutines.flow.Flow

interface IBurgerLocalRepository {
    fun getAll(): Flow<List<Burger>>
    suspend fun findById(id : Long): Burger
    suspend fun insert(burger: Burger): Long
    suspend fun delete(burger: Burger)
    suspend fun update(burger: Burger)
}