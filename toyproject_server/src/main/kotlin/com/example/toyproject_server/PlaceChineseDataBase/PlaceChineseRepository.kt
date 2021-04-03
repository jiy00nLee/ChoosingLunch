package com.example.toyproject_server.PlaceChineseDataBase

import com.example.toyproject_server.PlaceDatabase.Place

interface PlaceChineseRepository {

    fun save(place : PlaceChinese?)

    fun findByCategorygroupnameAndXAndY(query: String, userLng : Double, userLat: Double) : List<PlaceChinese>
}