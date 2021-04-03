package com.example.toyproject_client.myserver

import com.example.toyproject_client.data.UserData.AppDatabase
import com.example.toyproject_client.data.UserData.UserDataRepository

class MyserverRepository {

    companion object{
        private var sInstance: MyserverRepository? = null
        fun getInstance(database: AppDatabase): MyserverRepository {
            return sInstance
                ?: synchronized(this){
                    val instance = MyserverRepository()
                    sInstance = instance
                    instance
                }
        }
    }

    private lateinit var myserverAPI  : Myserver



}