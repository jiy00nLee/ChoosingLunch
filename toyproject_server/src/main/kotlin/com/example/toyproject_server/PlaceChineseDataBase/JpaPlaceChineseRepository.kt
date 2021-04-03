package com.example.toyproject_server.PlaceChineseDataBase

import com.example.toyproject_server.PlaceDatabase.Place
import com.example.toyproject_server.PlaceDatabase.PlaceRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPlaceChineseRepository : JpaRepository<PlaceChinese, String>, PlaceChineseRepository { //String은 'Place'의 id(pk).
    //커스텀할 DB function만
    override fun findByCategorygroupnameAndXAndY(query: String, userLng : Double, userLat: Double) : List<PlaceChinese>
}