package com.example.toyproject_server.PlaceWesternDataBase

import com.example.toyproject_server.PlaceKoreanDataBase.PlaceKorean
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPlaceWesternRepository : JpaRepository<PlaceWestern, String>, PlaceWesternRepository { //String은 'Place'의 id(pk).
    //커스텀할 DB function만
    override fun findByCategorygroupnameAndXAndY(query: String, userLng : Double, userLat: Double) : List<PlaceWestern>

}