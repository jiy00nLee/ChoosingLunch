package com.example.toyproject_client.myserver

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.toyproject_client.data.AppDatabase
import com.example.toyproject_client.data.StoreMenuItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyserverRepository {
    private val myserverAPI = Myserver.create() //서버 생성
    //private val call : okhttp3.Call = okhttp3.Call ->얘는 하나로 활용할 수 없나???

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
    val livedata_resultplacemenus : MutableLiveData<List<StoreMenuItem>> = MutableLiveData()
    val livedata_resultplacesAll : MutableLiveData<List<PlaceDocument>> = MutableLiveData()
    val livedata_resultplacesKorean : MutableLiveData<List<PlaceDocument>> = MutableLiveData()
    val livedata_resultplacesJapanese : MutableLiveData<List<PlaceDocument>> = MutableLiveData()
    val livedata_resultplacesChinese : MutableLiveData<List<PlaceDocument>> = MutableLiveData()
    val livedata_resultplacesWestern : MutableLiveData<List<PlaceDocument>> = MutableLiveData()


    fun getStoreList(storecategory : String, userLat: Double, userLng: Double, storeaddres: String ) {

        val call =  myserverAPI.getSearchLocationFromMyserverDatabase(storecategory, userLat, userLng, storeaddres)

        call.enqueue(object : Callback<List<PlaceDocument>> {
            //통신 성공
            override fun onResponse(call: Call<List<PlaceDocument>>, response: Response<List<PlaceDocument>>) {

                if (storecategory == "음식점") livedata_resultplacesAll.value = response.body()
                else if (storecategory == "한식") livedata_resultplacesKorean.value = response.body()
                else if (storecategory == "일식") livedata_resultplacesJapanese.value = response.body()
                else if (storecategory == "중식") livedata_resultplacesChinese.value = response.body()
                else if (storecategory == "양식") livedata_resultplacesWestern.value = response.body()

                //Log.e(ContentValues.TAG,"${livedata_resultplaces.value}")
            }
            //통신 실패
            override fun onFailure(call: Call<List<PlaceDocument>>, t: Throwable) {
                Log.e(ContentValues.TAG,"FAIL")
            }
        })
    }

    fun getStoreMenuList(storeid : String){
        val call = myserverAPI.getSearchStoreMenuFromMyserverDatabase(storeid)

        call.enqueue(object : Callback<List<StoreMenuItem>> {
            //통신 성공
            override fun onResponse(call: Call<List<StoreMenuItem>>, response: Response<List<StoreMenuItem>>) {
                livedata_resultplacemenus.value = response.body()
            }
            //통신 실패
            override fun onFailure(call: Call<List<StoreMenuItem>>, t: Throwable) {
                Log.e(ContentValues.TAG,"FAIL")
            }

        })
    }

    fun putPayInfoData(payInfoItem: PayInfoItem){
        val call = myserverAPI.InsertbuyStoreMenuInfoToMyserverDatabase(payInfoItem)
        call.enqueue(object : Callback<PayInfoItem>{
            override fun onResponse(call: Call<PayInfoItem>, response: Response<PayInfoItem>) {
                Log.d(ContentValues.TAG,"SUCCESS")
            }
            override fun onFailure(call: Call<PayInfoItem>, t: Throwable) {
                Log.e(ContentValues.TAG,"FAIL")
            }
        })
    }


}