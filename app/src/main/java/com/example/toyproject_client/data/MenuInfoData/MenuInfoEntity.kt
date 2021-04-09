package com.example.toyproject_client.data.MenuInfoData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mycartTable", primaryKeys = ["StoreID", "Menuname"])
data class MenuInfoEntity (

    @ColumnInfo(name="StoreID")
    val storeid: String = "",    //lateinit으로 초기화 해줘도 됨.

    @ColumnInfo(name="Menuname")
    val menuname : String = "",

    @ColumnInfo(name="Menuprice")
    val menuprice : Int = 0,

    @ColumnInfo(name= "Menucount")
    val menucount : Int = 0,

    @ColumnInfo(name = "Storename")
    val storename: String = ""
)