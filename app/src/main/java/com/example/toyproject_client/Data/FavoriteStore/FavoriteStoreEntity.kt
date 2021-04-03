package com.example.toyproject_client.data.FavoriteStore

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favoriteStoreTable")
data class FavoriteStoreEntity (

        //후에 id 추가해줘야함. -> 이름은 primary X.
        @PrimaryKey
        @ColumnInfo(name="userName")
        val username : String,

        @ColumnInfo(name="userAddress")
        val address : String,

        @ColumnInfo(name = "userLongtitude")
        val longtitude : Double,

        @ColumnInfo(name = "userLatitude")
        val latitude : Double,

        )