package com.example.toyproject_client.data.FavoriteStore

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.toyproject_client.data.UserData.UserDataEntity
import com.example.toyproject_client.myserver.PlaceDocument

@Dao //DTO
interface FavoriteStoreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // 동일 데이터의 경우 무시(반영 X)
    fun insertStoreData(placeDocument: PlaceDocument)   //찜한 가게를 저장한다.

    @Query("SELECT * from favoriteStoreTable")
    fun getFavoriteStoreData() : LiveData<UserDataEntity> //해당 사용자의 이름에 대한 위치정보를 받아온다. -> 애초에 앱저장소에는 본인 아이디만 저장되어있으므로 하나짜리 테이블!

}