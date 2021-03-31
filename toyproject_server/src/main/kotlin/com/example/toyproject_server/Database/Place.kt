package com.example.toyproject_server.Database

import org.hibernate.annotations.Proxy
import javax.persistence.*


@Entity
class Place {
    @Id
    var id: String =""
    lateinit var addressName: String
    lateinit var categoryGroupCode: String  //중요 카테고리만 그룹핑한 카테고리 '그룹 코드'
    lateinit var categoryGroupName: String  //중요 카테고리만 그룹핑한 카테고리 '그룹명'
    lateinit var distance: String
    lateinit var phone: String
    lateinit var placeName: String
    lateinit var placeUrl: String    //장소 상세페이지 URL
    lateinit var roadAddressName: String
    var x: Double? = null  // longitude
    var y: Double? = null  //  latitude
}