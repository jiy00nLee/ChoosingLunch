package com.example.toyproject_client.view

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.toyproject_client.data.AppDatabase
import com.example.toyproject_client.data.UserData.UserDataRepository
import com.example.toyproject_client.data.UserData.UserLocationItemData
import com.example.toyproject_client.myserver.Myserver
import com.example.toyproject_client.myserver.PlaceDocument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragViewmodel (application: Application) : AndroidViewModel(application) {

    private val userdatarepository = UserDataRepository(AppDatabase.getDatabase(application, viewModelScope))
    var livedata_resultplaces : MutableLiveData<List<PlaceDocument>?> = MutableLiveData()


    fun insertUserLocationData(it : UserLocationItemData) = viewModelScope.launch(Dispatchers.IO)  {
        userdatarepository.insertUserLocationData(it)
    }

    fun getUserLocationData() : LiveData<UserLocationItemData> {
        return userdatarepository.getUserLocationData()
    }


    fun getStoreList(storecategory : String, userLat: Double, userLng: Double, storeaddres: String ) {
        val myserverAPI = Myserver.create() //서버 생성
        val call =  myserverAPI.getSearchLocationFromMyserverDatabase(storecategory, userLat, userLng, storeaddres)

        call.enqueue(object : Callback<List<PlaceDocument>> {
            //통신 성공
            override fun onResponse(call: Call<List<PlaceDocument>>, response: Response<List<PlaceDocument>>) {
                livedata_resultplaces.value = response.body()
                Log.e(TAG,"${livedata_resultplaces.value}")
            }
            //통신 실패
            override fun onFailure(call: Call<List<PlaceDocument>>, t: Throwable) {
                Log.e(TAG,"FAIL")
            }
        })

        //val resultplaces = myserverAPI.getSearchLocationFromMyserver(storecategory, userLat, userLng).execute().body()
       // livedata_resultplaces.value = resultplaces
    }






}