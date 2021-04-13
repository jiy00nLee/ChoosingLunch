package com.example.toyproject_client.data.MenuInfoData

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.toyproject_client.data.AppDatabase
import com.example.toyproject_client.data.StoreMenuItem
import com.example.toyproject_client.data.favoriteStoreData.FavoriteStoreEntity
import com.example.toyproject_client.myserver.PlaceDocument

class NenuInfoRepository(appdatabase: AppDatabase) {

    private val menuinfoDao= appdatabase.MenuInfoDao()

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

    fun insertMenuInfoData(menuinfo : StoreMenuItem) {
        menuinfoDao.insertMenuInfoData(mappingStoreMenuItemTMenuInfoEntity(menuinfo))
    }
    fun deleteMenuInfoListByStore(storeID: String){
        menuinfoDao.deleteMenuListByStore(storeID)
    }

    fun getAllStoreIDsFromMycart() : LiveData<List<String>> {
        return menuinfoDao.getAllStoreIDsFromMycart()
    }

    fun getMenuInfoListByStore(storeID : String) : LiveData<List<StoreMenuItem>> {
        val menuInfoByStoreLiveData = Transformations.map(menuinfoDao.getMenuInfoListByStore(storeID)){ menuInfoEntityList ->menuInfoEntityList.map{ menuinfoentity ->
            mappingMenuInfoEntityToStoreMenuItem(menuinfoentity)  }
        }
        return menuInfoByStoreLiveData
    }


    private fun mappingMenuInfoEntityToStoreMenuItem(it : MenuInfoEntity) : StoreMenuItem {
        val storeid : String = it.storeid
        val menuname : String = it.menuname
        val menuprice : Int = it.menuprice
        val menucount : Int = it.menucount
        val storename : String = it.storename

        return StoreMenuItem(storeid, menuname, menuprice, menucount, storename)
    }

    private fun mappingStoreMenuItemTMenuInfoEntity(it : StoreMenuItem) : MenuInfoEntity {
        val storeid : String = it.storeid
        val menuname : String = it.menuname
        val menuprice : Int = it.menuprice
        val menucount : Int = it.menucount
        val storename : String = it.storename

        return MenuInfoEntity(storeid, menuname, menuprice, menucount, storename)
    }



}