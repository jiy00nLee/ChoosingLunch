package com.example.toyproject_client.myserver

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.toyproject_client.data.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyserverRepository {

    companion object{
        private var sInstance: MyserverRepository? = null
        fun getInstance(): MyserverRepository {
            return sInstance
                ?: synchronized(this){
                    val instance = MyserverRepository()
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