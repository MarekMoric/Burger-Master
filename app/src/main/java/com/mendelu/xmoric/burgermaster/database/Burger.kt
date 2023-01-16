package com.mendelu.xmoric.burgermaster.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "burgers")
class Burger(
    @ColumnInfo(name = "text") var text: String) : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

    @ColumnInfo(name = "name")
    var name: String? = "Burger"

    @ColumnInfo(name = "bread")
    var bread: String? = null

    @ColumnInfo(name = "meat")
    var meat: String? = null

    @ColumnInfo(name = "sauce")
    var sauce: String? = null

    @ColumnInfo(name = "extras")
    var extras: String? = null

}