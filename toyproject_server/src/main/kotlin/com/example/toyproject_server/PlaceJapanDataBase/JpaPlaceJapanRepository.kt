package com.example.toyproject_server.PlaceJapanDataBase

import com.example.toyproject_server.PlaceChineseDataBase.PlaceChinese
import com.example.toyproject_server.PlaceChineseDataBase.PlaceChineseRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPlaceJapanRepository : JpaRepository<PlaceJapan, String>, PlaceJapanRepository { //String은 'Place'의 id(pk).
    //커스텀할 DB function만
    override fun findByCategorygroupnameAndXAndY(query: String, userLng : Double, userLat: Double) : List<PlaceJapan>
}