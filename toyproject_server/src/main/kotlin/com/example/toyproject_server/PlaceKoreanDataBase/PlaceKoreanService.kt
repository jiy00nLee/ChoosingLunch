package com.example.toyproject_server.PlaceKoreanDataBase


import com.example.toyproject_server.PlaceJapanDataBase.PlaceJapan
import com.example.toyproject_server.RestAPI.KaKaoAPI
import com.example.toyproject_server.RestAPI.PlaceDocument
import com.example.toyproject_server.RestAPI.PlaceMeta
import com.example.toyproject_server.RestAPI.ResultSearchKeyword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class PlaceKoreanService {

    private val placeRepository : PlaceKoreanRepository

    @Autowired
    constructor(placeRepository : PlaceKoreanRepository){
        this.placeRepository = placeRepository
    }

    fun saveAll(query: String?, userLng : Double, userLat: Double)  {
        var page_num : Int = 1
        val kakaoapi = KaKaoAPI.create()
        var resultSearchedData : ResultSearchKeyword?
        var meta : PlaceMeta?
        var places : List<PlaceDocument>?
        var transed_places : List<PlaceKorean>?


        while (true){
            resultSearchedData = kakaoapi.getSearchLocationFromKakaoServer(query, userLat, userLng, page_num)?.execute()?.body()
            meta = resultSearchedData?.meta
            places = resultSearchedData?.documents
            transed_places = places?.map {it -> mappingPlaceDocumenttoPlace(it)}

            transed_places?.forEach { it -> placeRepository.save(it) } //placeRepository.saveAll(transed_places5) //이거 왜 안되는지 모르겠다.
            page_num = page_num + 1
            if (meta?.is_end == true) break
        }

    }

    fun getAll(query: String, userLng : Double, userLat: Double) : List<PlaceDocument>{
        val places =  placeRepository.findByCategorygroupnameAndXAndY(query, userLng, userLat)
        println("gotten from db "+ places)
        return places.map { it ->  mappingPlacetoPlaceDocument(it) }
    }

    private fun mappingPlaceDocumenttoPlace(placeDocument: PlaceDocument) : PlaceKorean {
        val place : PlaceKorean = PlaceKorean()
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

    private fun mappingPlacetoPlaceDocument(place: PlaceKorean) : PlaceDocument {
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
        return(placeDocument)
    }
}