package com.example.toyproject_client.data.favoriteStoreData

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao //DTO
interface FavoriteStoreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)                    //데이터가 들어오면 무시한다.
    fun insertFavoriteStoreInfo(favoriteStoreEntity: FavoriteStoreEntity)              //해당 사용자의 위치 정보를 저장한다.

    @Delete
    fun deleteFavoriteStoreInfo(favoriteStoreEntity: FavoriteStoreEntity)

    @Query("SELECT * from favoriteStoreTable")
    fun getAllFavoriteStores() : LiveData<List<FavoriteStoreEntity>> //즐겨찾기 등록된 가게의 정보를 모두 가져온다.

    @Query("SELECT ID from favoriteStoreTable WHERE ID = :storeID")
    fun checkFavoriteStore(storeID : String) : LiveData<String?>  //결과값이 일치할 경우 해당 ID만 가져옴. (ID는 프라이머리 값이므로 결과값은 '1개'이다.)
}