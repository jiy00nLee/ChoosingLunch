package com.example.toyproject_server.PlaceJapanDataBase

import com.example.toyproject_server.PlaceChineseDataBase.PlaceChinese

interface PlaceJapanRepository {

    fun save(place : PlaceJapan?)

    fun findByCategorygroupnameAndXAndY(query: String, userLng : Double, userLat: Double) : List<PlaceJapan>
}