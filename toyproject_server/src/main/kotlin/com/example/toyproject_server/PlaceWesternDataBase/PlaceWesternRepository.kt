package com.example.toyproject_server.PlaceWesternDataBase

import com.example.toyproject_server.PlaceDatabase.Place
import com.example.toyproject_server.PlaceKoreanDataBase.PlaceKorean

interface PlaceWesternRepository {

    fun save(place : PlaceWestern?)

    fun findAll() : List<PlaceWestern>

    fun findByCategorygroupnameAndXAndY(query: String, userLng : Double, userLat: Double) : List<PlaceWestern>
}