package com.example.toyproject_server.PlaceJapanDataBase

import com.example.toyproject_server.PlaceChineseDataBase.PlaceChinese
import com.example.toyproject_server.PlaceDatabase.Place
import com.example.toyproject_server.RestAPI.KaKaoAPI
import com.example.toyproject_server.RestAPI.PlaceDocument
import com.example.toyproject_server.RestAPI.PlaceMeta
import com.example.toyproject_server.RestAPI.ResultSearchKeyword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class PlaceJapanService {
    private val placeRepository : PlaceJapanRepository

    @Autowired
    constructor(placeRepository : PlaceJapanRepository){
        this.placeRepository = placeRepository
    }

    fun saveAll(query: String?, userLng : Double, userLat: Double) : MutableList<String>? {
        var page_num : Int = 1
        val kakaoapi = KaKaoAPI.create()
        var resultSearchedData : ResultSearchKeyword?
        var meta : PlaceMeta?
        var places : List<PlaceDocument>?
        var transed_places : List<PlaceJapan>?
        var store_ids : MutableList<String> = mutableListOf()


        while (true){
            resultSearchedData = kakaoapi.getSearchLocationFromKakaoServer(query, userLat, userLng, page_num)?.execute()?.body()
            meta = resultSearchedData?.meta
            places = resultSearchedData?.documents
            transed_places = places?.map {it ->
                store_ids.add(it.id)
                mappingPlaceDocumenttoPlace(it)}

            transed_places?.forEach { it -> placeRepository.save(it) } //placeRepository.saveAll(transed_places5) //이거 왜 안되는지 모르겠다.
            page_num = page_num + 1
            if (meta?.is_end == true) break
        }
        return store_ids
    }

    fun getAll(query: String, userLng : Double, userLat: Double) : List<PlaceDocument>{
        val listPlaces : List<PlaceJapan> = placeRepository.findAll()
        println(listPlaces)

        //리스트를 통째로 넘겨준 후 거리계산해서 해당되는 애들만 거리순으로 정리해 리스트 추출하기.
        val selectedlist : MutableList<PlaceJapan> = mutableListOf()
        listPlaces.forEach { place ->
            if (calculateDistance(userLng, userLat, place)) {
                selectedlist.add(place)
            }
        }
        println(selectedlist)
        return selectedlist.map{it -> mappingPlacetoPlaceDocument(it)} //변환 된 값 넣어줘야 함.
    }

    private fun calculateDistance(x: Double, y : Double, place : PlaceJapan) : Boolean {
        val theta : Double = place.x!! - x
        var distance : Double = Math.sin(Math.toRadians(place.y!!)) * Math.sin(Math.toRadians(y)) + Math.cos(
            Math.toRadians(
                (place.y!!)
            )
        ) * Math.cos(Math.toRadians((y))) * Math.cos(Math.toRadians(theta))

        distance = Math.acos(distance)
        distance = Math.toDegrees(distance)
        distance = distance*60*(1.1515)*(0.1609344) //단위 : 킬로미터(km) -> 원래는 1.609344를 곱해줘야함.

        println("distance " + distance)
        if (distance < 1000) {return true}  //1km이내인 정보만 출력해줌.
        return false
    }

    private fun mappingPlaceDocumenttoPlace(placeDocument: PlaceDocument) : PlaceJapan {
        val place : PlaceJapan = PlaceJapan()
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
        place.categoryname = "일식"
        return(place)
    }

    private fun mappingPlacetoPlaceDocument(place: PlaceJapan) : PlaceDocument {
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
        placeDocument.category_name = place.categoryname
        placeDocument.distance     // !!!!!!!!!!!!!!!!!!
        return(placeDocument)
    }

}