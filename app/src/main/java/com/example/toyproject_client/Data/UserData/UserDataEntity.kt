package com.example.toyproject_client.data.UserData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userdataTable")
data class UserDataEntity (

    //후에 id 추가해줘야함. -> 이름은 primary X.
    @PrimaryKey
    @ColumnInfo(name="id")
    val id : String,

    @ColumnInfo
    val addressname : String,

    @ColumnInfo(name="userAddress")
    val address : String,

    @ColumnInfo(name = "userLongtitude")
    val longtitude : Double,

    @ColumnInfo(name = "userLatitude")
    val latitude : Double,

)