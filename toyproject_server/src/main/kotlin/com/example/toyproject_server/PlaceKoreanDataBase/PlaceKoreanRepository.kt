package com.example.toyproject_server.PlaceKoreanDataBase

import com.example.toyproject_server.PlaceJapanDataBase.PlaceJapan

interface PlaceKoreanRepository {

    fun save(place : PlaceKorean?)

    fun findByCategorygroupnameAndXAndY(query: String, userLng : Double, userLat: Double) : List<PlaceKorean>
}