package com.example.toyproject_client.Data.UserData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class UserDataRepository(locationdatabase: AppDatabase){

    private val userdataDao = locationdatabase.UserDataDao()

    companion object{
        private var sInstance: UserDataRepository? = null
        fun getInstance(database: AppDatabase): UserDataRepository {
            return sInstance
                ?: synchronized(this){
                    val instance = UserDataRepository(database)
                    sInstance = instance
                    instance
                }
        }
    }

    private fun mappingUserDataItemToUserDataEntity(it : UserLocationItemData) : UserDataEntity {
        val username : String = it.username
        val address : String = it.address
        val longtitude : Double = it.longtitude
        val latitude : Double = it.latitude
        return (UserDataEntity(username, address, longtitude, latitude))
    }
    private fun mappingUserDataEntityToUserDataItem(it : UserDataEntity) : UserLocationItemData {
        val username : String = it.username
        val address : String = it.address
        val longtitude : Double = it.longtitude
        val latitude : Double = it.latitude
        return (UserLocationItemData(username, address, longtitude, latitude))
    }

    fun insertUserLocationData(it : UserLocationItemData) {
        userdataDao.insertUserLocationData(mappingUserDataItemToUserDataEntity(it))
    }

    fun getUserLocationData() : LiveData<UserLocationItemData>{
        val userlocationitemdataLiveData = Transformations.map(userdataDao.getUserLoacationData()){
            mappingUserDataEntityToUserDataItem(it)
        }
        return userlocationitemdataLiveData
    }

    fun getUserName() : String {
        return userdataDao.getUserName()
    }

}