package com.example.toyproject_client.data.favoriteStoreData


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.toyproject_client.data.AppDatabase
import com.example.toyproject_client.myserver.PlaceDocument

class FavoriteStoreRepository(appdatabase: AppDatabase){

    companion object{
        private var sInstance: FavoriteStoreRepository? = null
        fun getInstance(database: AppDatabase): FavoriteStoreRepository {
            return sInstance
                ?: synchronized(this){
                    val instance = FavoriteStoreRepository(database)
                    sInstance = instance
                    instance
                }
        }
    }

    private val favoriteStoreDao = appdatabase.FavoriteStoreDao()

    fun insertFavoriteStore(receivedStoredata : PlaceDocument){
        favoriteStoreDao.insertFavoriteStoreInfo( mappingPlaceDocumentToFavoriteStoreEntity(receivedStoredata) )
    }

    fun getAllFavoriteStores() : LiveData<List<PlaceDocument>?>{
        val userlocationitemdataLiveData = Transformations.map(favoriteStoreDao.getAllFavoriteStores()){ entitylist -> entitylist?.map{ entity ->
            mappingFavoriteStireEntityToPlaceDocument(entity)
             }
        }
        return userlocationitemdataLiveData
    }

    private fun mappingFavoriteStireEntityToPlaceDocument(it : FavoriteStoreEntity) : PlaceDocument{
        val storeID : String = it.id
        val addressName : String = it.address_name
        val categoryGroupCode : String = it.category_group_code
        val categoryGroupName : String = it.category_group_name
        val categoryName : String = it.category_name
        val phone : String = it.phone
        val placeName : String = it.place_name
        val placeURL : String = it.place_url
        val roadAddressName : String = it.road_address_name
        val x : Double? = it.x
        val y : Double? = it.y

        return PlaceDocument(storeID, addressName, categoryGroupCode,  categoryGroupName, categoryName, phone, placeName,
            placeURL, roadAddressName, x, y)
    }

    private fun mappingPlaceDocumentToFavoriteStoreEntity(it : PlaceDocument) : FavoriteStoreEntity {
        val storeID : String = it.id
        val addressName : String = it.address_name
        val categoryGroupCode : String = it.category_group_code
        val categoryGroupName : String = it.category_group_name
        val categoryName : String = it.category_name
        val phone : String = it.phone
        val placeName : String = it.place_name
        val placeURL : String = it.place_url
        val roadAddressName : String = it.road_address_name
        val x : Double? = it.x
        val y : Double? = it.y

        return FavoriteStoreEntity(storeID, addressName, categoryGroupCode,  categoryGroupName, categoryName, phone, placeName,
        placeURL, roadAddressName, x, y)
    }



}