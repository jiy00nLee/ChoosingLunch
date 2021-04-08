package com.example.toyproject_client.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.toyproject_client.data.favoriteStoreData.FavoriteStoreRepository
import com.example.toyproject_client.myserver.PlaceDocument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteStoreViewmodel (application: Application) : AndroidViewModel(application) {

    private val favoriteStoreRepository = FavoriteStoreRepository.getInstance(AppDatabase.getDatabase(application, viewModelScope))

    /*
    companion object{
        var checkingStoreName : String? = " "   //이렇게 했을때 코루틴값이 반영 도나?!!!!!!!
    }*/

    fun insertFavoriteStore(receivedStoredata : PlaceDocument)  = viewModelScope.launch(Dispatchers.IO)  {
        favoriteStoreRepository.insertFavoriteStore(receivedStoredata)
    }
    fun deleteFavoriteStore(receivedStoredata: PlaceDocument) = viewModelScope.launch(Dispatchers.IO) {
        favoriteStoreRepository.deleteFavoriteStore(receivedStoredata)
    }

    fun checkFavoriteStore(storeID : String) : LiveData<String?> {
        return favoriteStoreRepository.checkFavoriteStore(storeID)
    }

    fun getAllFavoriteStores() : LiveData<List<PlaceDocument>?> {
        return favoriteStoreRepository.getAllFavoriteStores()
    }


}