package com.example.toyproject_server.PlaceKoreanDataBase

import com.example.toyproject_server.PlaceJapanDataBase.PlaceJapan
import com.example.toyproject_server.PlaceJapanDataBase.PlaceJapanRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPlaceKoreanRepository  : JpaRepository<PlaceKorean, String>, PlaceKoreanRepository { //String은 'Place'의 id(pk).
    //커스텀할 DB function만
    override fun findByCategorygroupnameAndXAndY(query: String, userLng : Double, userLat: Double) : List<PlaceKorean>

}
