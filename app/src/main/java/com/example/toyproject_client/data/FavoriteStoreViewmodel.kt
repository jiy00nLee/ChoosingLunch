package com.example.toyproject_client.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.toyproject_client.data.favoriteStoreData.FavoriteStoreRepository
import com.example.toyproject_client.myserver.PlaceDocument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteStoreViewmodel (application: Application) : AndroidViewModel(application) {
    private val favoriteStoreRepository = FavoriteStoreRepository(AppDatabase.getDatabase(application, viewModelScope))

    fun insertFavoriteStore(receivedStoredata : PlaceDocument)  = viewModelScope.launch(Dispatchers.IO)  {
        favoriteStoreRepository.insertFavoriteStore(receivedStoredata)
    }

    fun getAllFavoriteStores() : LiveData<List<PlaceDocument>?> {
        return favoriteStoreRepository.getAllFavoriteStores()
    }


}