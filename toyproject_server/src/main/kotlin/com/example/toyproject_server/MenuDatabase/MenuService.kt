package com.example.toyproject_server.MenuDatabase

import com.example.toyproject_server.PlaceDatabase.Place
import com.example.toyproject_server.RestAPI.KaKaoAPI
import com.example.toyproject_server.RestAPI.PlaceDocument
import com.example.toyproject_server.RestAPI.PlaceMeta
import com.example.toyproject_server.RestAPI.ResultSearchKeyword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class MenuService {

    private val menuRepository : MenuRepository

    @Autowired
    constructor(menuRepository: MenuRepository){
        this.menuRepository = menuRepository
    }
/*
    //여기서 함수이름은 노상관 인듯? (ex. join)
    fun saveAll(query: String?, userLng : Double, userLat: Double)  {
        var page_num : Int = 1
        val kakaoapi = KaKaoAPI.create()
        var resultSearchedData : ResultSearchKeyword?
        var meta : PlaceMeta?
        var places : List<PlaceDocument>?
        var transed_places : List<Place>?

        while (true){

            resultSearchedData = kakaoapi.getSearchLocationFromKakaoServer(query, userLat, userLng, page_num).execute().body()
            meta = resultSearchedData?.meta
            places = resultSearchedData?.documents
            transed_places = places?.map {it -> mappingPlaceDocumenttoPlace(it)}

            transed_places?.forEach { it -> placeRepository.save(it) } //placeRepository.saveAll(transed_places5) //이거 왜 안되는지 모르겠다.
            page_num = page_num + 1
            if (meta?.is_end == true) break
        }

    }
*/



}