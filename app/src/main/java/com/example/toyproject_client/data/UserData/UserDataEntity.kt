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

    @PrimaryKey
    @ColumnInfo(name="id")         //관련된 UserlocationItemdata쪽도 바꿔주기!!!
    val id : String  = "ljy3237" //로그인 구현시 -> 제대로 구현해줘야함. (일단 유저 하나라고 가정하고 걍 상수 박기)

)