package com.example.toyproject_client.data.MenuInfoData

import com.example.toyproject_client.data.AppDatabase

class NenuInfoRepository(appdatabase: AppDatabase) {

    private val menuInfoDao= appdatabase.MenuInfoDao()

    companion object {
        private var sInstance: NenuInfoRepository? = null

        fun getInstance(database: AppDatabase): NenuInfoRepository {
            return sInstance
                ?: synchronized(this) {
                    val instance = NenuInfoRepository(database)
                    sInstance = instance
                    instance
                }
        }
    }


}