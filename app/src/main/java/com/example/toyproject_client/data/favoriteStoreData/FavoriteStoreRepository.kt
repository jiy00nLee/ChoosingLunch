package com.example.toyproject_client.Data.favoriteStoreData

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.toyproject_client.data.AppDatabase
import com.example.toyproject_client.data.UserData.UserDataRepository
import com.example.toyproject_client.myserver.Myserver
import com.example.toyproject_client.myserver.PlaceDocument
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteStoreRepository(){

    companion object{
        private var sInstance: FavoriteStoreRepository? = null
        fun getInstance(): FavoriteStoreRepository {
            return sInstance
                ?: synchronized(this){
                    val instance = FavoriteStoreRepository()
                    sInstance = instance
                    instance
                }
        }
    }
    


}