package com.example.toyproject_client.data.MenuInfoData

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao //DTO
interface MenuInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)      //데이터가 들어오면 바꿔끼운다. -> 개수 수정 및 추가 삭제 함수 필요!!!
    fun insertMenuInfoData(menuinfo : MenuInfoEntity)

   //@Query("INs") 추가 구현 필요한 거 -> 삽입 수정 삭제 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    @Query("DELETE from mycartTable WHERE StoreID = :storeID")
    fun deleteMenuListByStore(storeID : String)

    //가게 별로 가져올 수 있게 구현해주어야 함!!!!!!!!!!!!!
    @Query("SELECT DISTINCT StoreID from mycartTable ORDER BY Storename ASC")
    fun getAllStoreIDsFromMycart() : LiveData<List<String>>    //장바구니에 저장되어 있는 모든 가게 이름들 가져옴.(중복제거되나? X, )


    @Query("SELECT * from mycartTable WHERE StoreID = :storeID ")
    fun getMenuInfoListByStore(storeID : String) : LiveData<List<MenuInfoEntity>>     // 한 가게의 다량의 메뉴들(list)


}