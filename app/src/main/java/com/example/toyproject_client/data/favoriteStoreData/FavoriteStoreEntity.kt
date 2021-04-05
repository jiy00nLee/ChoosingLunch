package com.example.toyproject_client.Data.favoriteStoreData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteStoreTable")
data class FavoriteStoreEntity (

    @PrimaryKey
    @ColumnInfo(name="ID")
    val id: String = "",    //lateinit으로 초기화 해줘도 됨.

    @ColumnInfo(name="Addressname")
    val address_name: String = "",

    @ColumnInfo(name="Categorygroupcode")
    val category_group_code: String = "",

    @ColumnInfo(name="Categorygroupname")
    val category_group_name: String = "",

    @ColumnInfo(name="Categoryname")
    val category_name: String = "",

    @ColumnInfo(name="Phone")
    val phone: String = "",

    @ColumnInfo(name="Placename")
    val place_name: String = "",

    @ColumnInfo(name="PlaceUrl")
    val place_url: String = "",

    @ColumnInfo(name="RoadAddressname")
    val road_address_name: String = "",

    @ColumnInfo(name="Longtitude")
    val x: Double? = null,

    @ColumnInfo(name="Latitude")
    val y: Double? = null
)