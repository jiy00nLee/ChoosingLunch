package com.example.toyproject_client.data.UserData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userdataTable")
data class UserDataEntity (

    @ColumnInfo(name = "userName")
    val username : String,

    @ColumnInfo(name="userAddress")
    val address : String,

    @ColumnInfo(name = "userLongtitude")
    val longtitude : Double,

    @ColumnInfo(name = "userLatitude")
    val latitude : Double,

    @PrimaryKey(autoGenerate = true) //후에 id(String) 추가해줘야함. -> 이름은 primary X.
    @ColumnInfo(name="id")
    val id : Int  = 0

)