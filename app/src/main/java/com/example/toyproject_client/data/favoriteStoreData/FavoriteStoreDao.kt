package com.example.toyproject_client.Data.favoriteStoreData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao //DTO
interface FavoriteStoreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)                    //데이터가 들어오면 무시한다.
    fun insertFavoriteStoreInfo(favoriteStoreEntity: FavoriteStoreEntity)              //해당 사용자의 위치 정보를 저장한다.

    @Query("SELECT * from favoriteStoreTable")
    fun getAllFavoriteStores() : LiveData<List<FavoriteStoreEntity>> //즐겨찾기 등록된 가게의 정보를 모두 가져온다.
}