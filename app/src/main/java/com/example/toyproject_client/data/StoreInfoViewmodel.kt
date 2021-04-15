package com.example.toyproject_client.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.toyproject_client.data.MenuInfoData.MenuInfoEntity
import com.example.toyproject_client.data.MenuInfoData.NenuInfoRepository
import com.example.toyproject_client.myserver.MyserverRepository
import com.example.toyproject_client.myserver.PayInfoItem
import com.example.toyproject_client.myserver.PlaceDocument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoreInfoViewmodel (application: Application) : AndroidViewModel(application)  {

    private val myserverRepository = MyserverRepository.getInstance()
    private val menuInfoRepository =  NenuInfoRepository.getInstance(AppDatabase.getDatabase(application, viewModelScope))


    fun getStoreMenuList(storeid : String) : MutableLiveData<List<StoreMenuItem>> {
        myserverRepository.getStoreMenuList(storeid)
        return myserverRepository.livedata_resultplacemenus
    }
    fun insertMenuInfoData(menuinfo : StoreMenuItem) = viewModelScope.launch(Dispatchers.IO) {
        menuInfoRepository.insertMenuInfoData(menuinfo)
    }
    fun deleteMenuInfoListByStore(storeID: String) = viewModelScope.launch(Dispatchers.IO){
        menuInfoRepository.deleteMenuInfoListByStore(storeID)
    }

    fun getAllStoreIDsFromMycart() : LiveData<List<String>> {
        return menuInfoRepository.getAllStoreIDsFromMycart()
    }

    fun getMenuInfoListByStore(storeID : String) : LiveData<List<StoreMenuItem>>{
        return menuInfoRepository.getMenuInfoListByStore(storeID)
    }

}