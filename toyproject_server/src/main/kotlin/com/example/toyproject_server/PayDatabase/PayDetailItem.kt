package com.example.toyproject_server.PayDatabase


data class PayDetailItem (
    val storename : String = "",
    val menuinfotext : String = "",
    val totalmenuprice : Int = 0,
    val storeid : String = "",
    val menupriceinfotext : String = "",
    var clickedstate : Boolean = false
)