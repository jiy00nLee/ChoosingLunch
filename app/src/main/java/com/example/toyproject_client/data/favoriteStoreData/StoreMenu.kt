package com.example.toyproject_client.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreMenuItem (
    val storeid : String = "",
    val menuname : String = "",
    val menuprice : Int = 0
) : Parcelable