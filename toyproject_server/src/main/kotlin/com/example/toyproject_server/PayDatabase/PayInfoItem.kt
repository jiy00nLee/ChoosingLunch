package com.example.toyproject_server.PayDatabase

import kotlin.collections.ArrayList


data class PayInfoItem (
    val userid : String = "",
    val username : String = "",
    val payaddress : String = "",
    val paydaytime : String = "",
    val paymethod : String = "",
    val totalpayPrice : Int = 0,
    val paydetailData : ArrayList<PayDetailItem> = arrayListOf()
)