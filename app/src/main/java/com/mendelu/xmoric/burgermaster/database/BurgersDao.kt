package com.mendelu.xmoric.burgermaster.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BurgersDao {
    @Query("SELECT * FROM burgers")
    fun getAll(): Flow<List<Burger>>

    @Query("SELECT * FROM burgers WHERE id = :id")
    suspend fun findById(id : Long): Burger

    @Insert
    suspend fun insert(burger: Burger): Long

    @Update
    suspend fun update(burger: Burger)

    @Delete
    suspend fun delete(burger: Burger)
}