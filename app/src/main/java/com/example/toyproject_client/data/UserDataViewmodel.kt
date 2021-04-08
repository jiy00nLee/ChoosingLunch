package com.example.toyproject_client.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.toyproject_client.data.UserData.UserDataRepository
import com.example.toyproject_client.data.UserData.UserLocationItemData
import com.example.toyproject_client.myserver.MyserverRepository
import com.example.toyproject_client.myserver.PlaceDocument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDataViewmodel (application: Application) : AndroidViewModel(application) {

    private val userdataRepository = UserDataRepository.getInstance(AppDatabase.getDatabase(application, viewModelScope))
    private val myserverRepository = MyserverRepository.getInstance()


    fun insertUserLocationData(it : UserLocationItemData) = viewModelScope.launch(Dispatchers.IO)  {
        userdataRepository.insertUserLocationData(it)
    }

    fun getUserLocationData() : LiveData<UserLocationItemData> {
        return userdataRepository.getUserLocationData()
    }


    fun getStoreList(categoryname : String, userLat: Double, userLng: Double, storeaddres: String ) : MutableLiveData<List<PlaceDocument>>  {

        myserverRepository.getStoreList(categoryname, userLat, userLng, storeaddres)
        lateinit var Resultlist : MutableLiveData<List<PlaceDocument>>

        if (categoryname == "음식점") Resultlist= myserverRepository.livedata_resultplacesAll
        else if (categoryname == "한식") Resultlist= myserverRepository.livedata_resultplacesKorean
        else if (categoryname == "일식") Resultlist= myserverRepository.livedata_resultplacesJapanese
        else if (categoryname == "중식") Resultlist= myserverRepository.livedata_resultplacesChinese
        else if (categoryname == "양식") Resultlist= myserverRepository.livedata_resultplacesWestern

        return Resultlist

        //val resultplaces = myserverAPI.getSearchLocationFromMyserver(storecategory, userLat, userLng).execute().body()
       // livedata_resultplaces.value = resultplaces
    }




}