package com.example.toyproject_server.PlaceWesternDataBase

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class PlaceWestern {
    @Id
    var id: String =""
    lateinit var addressname: String
    lateinit var categorygroupcode: String  //중요 카테고리만 그룹핑한 카테고리 '그룹 코드'
    lateinit var categorygroupname: String  //중요 카테고리만 그룹핑한 카테고리 '그룹명'
    lateinit var phone: String
    lateinit var placename: String
    lateinit var placeurl: String    //장소 상세페이지 URL
    lateinit var roadaddressname: String
    var x: Double? = null  // longitude
    var y: Double? = null  //  latitude
    lateinit var categoryname : String  //임의로 만들어준 값
}