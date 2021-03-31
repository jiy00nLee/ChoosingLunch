package com.example.toyproject_client.Data.UserData


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//@Parcelize
data class UserLocationItemData (
    val username : String,
    val address : String,
    val longtitude : Double,
    val latitude : Double
) //: Parcelable