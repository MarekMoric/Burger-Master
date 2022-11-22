package com.mendelu.xmoric.burgermaster.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Burger::class], version = 1, exportSchema = false)
abstract class BurgerDatabase : RoomDatabase() {

    abstract fun burgersDao(): BurgersDao

    companion object {

        private var INSTANCE: BurgerDatabase? = null

        fun getDatabase(context: Context): BurgerDatabase {
            if (INSTANCE == null) {
                synchronized(BurgerDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            BurgerDatabase::class.java, "burger_database"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}