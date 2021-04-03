package com.example.toyproject_client.data.UserData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao //DTO
interface UserDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //데이터가 들어오면 바꿔끼운다.
    fun insertUserLocationData(userdata : UserDataEntity)              //해당 사용자의 위치 정보를 저장한다.

    @Query("SELECT * from userdataTable LIMIT 1 ")
    fun getUserLoacationData() : LiveData<UserDataEntity> //해당 사용자의 이름에 대한 위치정보를 받아온다. -> 애초에 앱저장소에는 본인 아이디만 저장되어있으므로 하나짜리 테이블!

}