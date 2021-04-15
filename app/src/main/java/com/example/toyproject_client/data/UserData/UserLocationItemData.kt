package com.example.toyproject_client.data.UserData


//@Parcelize
data class UserLocationItemData (
    val username : String = "이지윤",
    val address : String,
    val longtitude : Double,
    val latitude : Double,
    val id : String = "ljy3237"
) //: Parcelable