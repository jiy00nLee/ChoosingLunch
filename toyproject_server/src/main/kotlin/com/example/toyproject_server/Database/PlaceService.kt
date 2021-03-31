package com.example.toyproject_server.Database

import com.example.toyproject_server.RestAPI.KaKaoAPI
import com.example.toyproject_server.RestAPI.PlaceDocument
import com.example.toyproject_server.RestAPI.PlaceMeta
import com.example.toyproject_server.RestAPI.ResultSearchKeyword
import org.springframework.transaction.annotation.Transactional

@Transactional
class PlaceService {

    private val placeRepository : PlaceRepository

    constructor(placeRepository: PlaceRepository){
        this.placeRepository = placeRepository
    }

    //여기서 함수이름은 노상관 인듯? (ex. join)
    fun saveAll(query: String?)  {
        var page_num : Int = 1
        val kakaoapi = KaKaoAPI.create()
        var resultSearchedData : ResultSearchKeyword?
        var meta : PlaceMeta?
        var places : List<PlaceDocument>?
        var transed_places : List<Place>?


        //println("일단 메타 데이터 뽑기!!!!" + kakaoapi.getSearchLocationFromKakaoServer(query, 129.059111, 35.157662, i ).execute().body()
        while (true){   //안드로이드에서 받아와서 변수 넣어줘야함. (query, nowLng, nowLat)
            resultSearchedData = kakaoapi.getSearchLocationFromKakaoServer(query, 129.059111, 35.157662, page_num).execute().body()
            meta = resultSearchedData?.meta
            places = resultSearchedData?.documents
            transed_places = places?.map {it -> mappingPlaceDocumenttoPlace(it)}

            transed_places?.forEach { it -> placeRepository.save(it) } //placeRepository.saveAll(transed_places5) //이거 왜 안되는지 모르겠다.
            page_num = page_num + 1
            if (meta?.is_end == true) break
        }

    }


    fun getAll(query: String?) : List<Place>?{
        return placeRepository.findAll()
    }

    private fun mappingPlaceDocumenttoPlace(placeDocument: PlaceDocument) : Place{
        val place : Place = Place()
        place.id = placeDocument.id
        place.addressName = placeDocument.address_name
        place.categoryGroupCode = placeDocument.category_group_code
        place.categoryGroupName = placeDocument.category_group_name
        place.distance = placeDocument.distance
        place.phone = placeDocument.phone
        place.placeName = placeDocument.place_name
        place.placeUrl = placeDocument.place_url
        place.roadAddressName = placeDocument.road_address_name
        place.x =placeDocument.x
        place.y= placeDocument.y
        return(place)
    }

}