package com.example.toyproject_server.PlaceDatabase

import com.example.toyproject_server.RestAPI.KaKaoAPI
import com.example.toyproject_server.RestAPI.PlaceDocument
import com.example.toyproject_server.RestAPI.PlaceMeta
import com.example.toyproject_server.RestAPI.ResultSearchKeyword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class PlaceService {

    private val placeRepository : PlaceRepository

    @Autowired
    constructor(placeRepository: PlaceRepository){
        this.placeRepository = placeRepository
    }

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


    fun getAll(query: String, userLng : Double, userLat: Double) : List<PlaceDocument>{
        val places: List<Place> =  placeRepository.findByCategorygroupnameAndXAndY(query, userLng, userLat) //getAll로 바꾸기.
        println("gotten from db "+ places)
        return places.map { it ->  mappingPlacetoPlaceDocument(it) }    //이때 타입을 바꿔주면 안될듯 (return값 "엔티티" )
    }
/*
    fun getLocationItems(query: String, userLng : Double, userLat: Double) : List<PlaceDocument>{
        val allEntities : List<Place> = placeRepository.findAll()
        allEntities?.forEach {  place ->
            calculateDistance(userLng,userLat, place) //  나중에 인풋으로 거리값 넣어주기!(ex.몇키로 이내)
        }

        //변환 된 값 넣어줘야 함.
    }

    private fun calculateDistance(x: Double?, y : Double?, place : Place) : Double? {
        val dataX : Double? = place.x
        val dataY : Double? = place.y

        val gottenX : Double? = x
        val
    }
    */


    private fun mappingPlaceDocumenttoPlace(placeDocument: PlaceDocument) : Place {
        val place : Place = Place()
        place.id = placeDocument.id
        place.addressname = placeDocument.address_name
        place.categorygroupcode = placeDocument.category_group_code
        place.categorygroupname = placeDocument.category_group_name
        place.phone = placeDocument.phone
        place.placename = placeDocument.place_name
        place.placeurl = placeDocument.place_url
        place.roadaddressname = placeDocument.road_address_name
        place.x =placeDocument.x
        place.y= placeDocument.y
        return(place)
    }

    private fun mappingPlacetoPlaceDocument(place: Place) : PlaceDocument{
        val placeDocument : PlaceDocument = PlaceDocument()
        placeDocument.id = place.id
        placeDocument.address_name = place.addressname
        placeDocument.category_group_name = place.categorygroupname
        placeDocument.category_group_code = place.categorygroupcode
        placeDocument.phone = place.phone
        placeDocument.place_name = place.placename
        placeDocument.place_url = place.placeurl
        placeDocument.road_address_name = place.roadaddressname
        placeDocument.x = place.x
        placeDocument.y = place.y
        //placeDocument.distance = calculateDistance(place.x, place.y)
        return(placeDocument)
    }



}