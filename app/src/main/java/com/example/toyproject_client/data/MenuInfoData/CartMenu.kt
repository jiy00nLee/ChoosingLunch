package com.example.toyproject_client.data.MenuInfoData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class CartMenuItem (       //단순 뷰 표출용 데이터 클래스
        val storename : String = "",
        val menuinfotext : String = "",
        val totalmenuprice : Int = 0,
        val storeid : String = "",
        val menupriceinfotext : String = "",
        var clickedstate : Boolean = false
)