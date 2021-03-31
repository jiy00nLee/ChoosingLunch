package com.example.toyproject_client.Data.UserData

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDataViewmodel (application: Application) : AndroidViewModel(application) {

    private val repository = UserDataRepository(AppDatabase.getDatabase(application, viewModelScope))


    fun insertUserLocationData(it : UserLocationItemData) = viewModelScope.launch(Dispatchers.IO)  {
        repository.insertUserLocationData(it)
    }

    fun getUserLocationData() : LiveData<UserLocationItemData> {
        return repository.getUserLocationData()
    }


}