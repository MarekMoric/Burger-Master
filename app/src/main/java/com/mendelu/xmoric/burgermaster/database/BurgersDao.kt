package com.mendelu.xmoric.burgermaster.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BurgersDao {
    @Query("SELECT * FROM burgers")
    fun getAll(): Flow<List<Burger>>

    @Update
    suspend fun update(burger: Burger)

    @Delete
    suspend fun delete(burger: Burger)
}