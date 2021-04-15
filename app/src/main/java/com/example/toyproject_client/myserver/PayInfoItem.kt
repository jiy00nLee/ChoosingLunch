package com.example.toyproject_client.myserver

import android.os.Parcelable
import com.example.toyproject_client.data.MenuInfoData.CartMenuItem
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class PayInfoItem (
        @SerializedName("userid") val userid : String = "",
        @SerializedName("username") val username : String = "",
        @SerializedName("payaddress") val payaddress : String = "",
        @SerializedName("paydaytime") var paydaytime : String = "",
        @SerializedName("paymethod") val paymethod : String = "",
        @SerializedName("totalpayPrice") val totalpayPrice : Int = 0,
        @SerializedName("paydetailData") val paydetailData : ArrayList<CartMenuItem> = arrayListOf() //이렇게 안되나?
) : Parcelable