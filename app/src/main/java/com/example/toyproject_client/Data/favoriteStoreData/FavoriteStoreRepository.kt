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

    var livedata_resultplaces : MutableLiveData<List<PlaceDocument>?> = MutableLiveData()

    fun getStoreList(storecategory : String, userLat: Double, userLng: Double, storeaddres: String ) {

        val myserverAPI = Myserver.create() //서버 생성
        val call =  myserverAPI.getSearchLocationFromMyserverDatabase(storecategory, userLat, userLng, storeaddres)

        call.enqueue(object : Callback<List<PlaceDocument>> {
            //통신 성공
            override fun onResponse(call: Call<List<PlaceDocument>>, response: Response<List<PlaceDocument>>) {
                livedata_resultplaces.value = response.body()
                Log.e(ContentValues.TAG,"${livedata_resultplaces.value}")
            }
            //통신 실패
            override fun onFailure(call: Call<List<PlaceDocument>>, t: Throwable) {
                Log.e(ContentValues.TAG,"FAIL")
            }
        })
    }


}