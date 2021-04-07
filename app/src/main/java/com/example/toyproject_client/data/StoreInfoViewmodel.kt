package com.example.toyproject_client.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.toyproject_client.myserver.MyserverRepository
import com.example.toyproject_client.myserver.PlaceDocument

class StoreInfoViewmodel (application: Application) : AndroidViewModel(application)  {

    private val myserverRepository = MyserverRepository.getInstance()

    fun getStoreMenuList(storeid : String) : MutableLiveData<List<StoreMenuItem>?> {
        myserverRepository.getStoreMenuList(storeid)
        return myserverRepository.livedata_resultplacemenus
    }

}