package com.example.toyproject_server.PlaceDatabase

import com.example.toyproject_server.RestAPI.KaKaoAPI
import com.example.toyproject_server.RestAPI.PlaceDocument
import com.example.toyproject_server.RestAPI.PlaceMeta
import com.example.toyproject_server.RestAPI.ResultSearchKeyword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.lang.Math.*


@Transactional
class PlaceService {
    private val R = 6372.8 * 1000

    private val placeRepository : PlaceRepository
    @Autowired
    constructor(placeRepository: PlaceRepository){
        this.placeRepository = placeRepository
    }

    //여기서 함수이름은 노상관 인듯? (ex. join)
    fun saveAll(query: String?, userLng : Double, userLat: Double, category: String) : MutableList<String>? {
        var page_num : Int = 1
        val kakaoapi = KaKaoAPI.create()
        var resultSearchedData : ResultSearchKeyword?
        var meta : PlaceMeta?
        var places : List<PlaceDocument>?
        var transed_places : List<Place>?
        var store_ids : MutableList<String> = mutableListOf()


        while (true){
            resultSearchedData = kakaoapi.getSearchLocationFromKakaoServer(query, userLat, userLng, page_num).execute().body()
            meta = resultSearchedData?.meta
            places = resultSearchedData?.documents
            transed_places = places?.map {it -> mappingPlaceDocumenttoPlace(it, category)}

            transed_places?.forEach { it ->
                store_ids.add(it.id)
                placeRepository.save(it) } //placeRepository.saveAll(transed_places5) //이거 왜 안되는지 모르겠다.
            page_num = page_num + 1
            if (meta?.is_end == true) break
        }
        return store_ids
    }


    fun getCategoryAll(category: String, userLng : Double, userLat: Double) : List<PlaceDocument>{
        val listPlaces : List<Place> = placeRepository.findByCategoryname(category)

        //리스트를 통째로 넘겨준 후 거리계산해서 해당되는 애들만 거리순으로 정리해 리스트 추출하기.
        val selectedlist : MutableList<Place> = mutableListOf()
        listPlaces.forEach { place ->
            if (calculateDistance(userLng, userLat, place)) {
                selectedlist.add(place)
            }
        }
        println(selectedlist)
        return selectedlist.map{it -> mappingPlacetoPlaceDocument(it)} //변환 된 값 넣어줘야 함.
    }



    fun getAll(userLng : Double, userLat: Double) : List<PlaceDocument>{
        val listPlaces : List<Place> = placeRepository.findAll()

        //리스트를 통째로 넘겨준 후 거리계산해서 해당되는 애들만 거리순으로 정리해 리스트 추출하기.
        val selectedlist : MutableList<Place> = mutableListOf()
        listPlaces.forEach { place ->
            if (calculateDistance(userLng, userLat, place)) {
                selectedlist.add(place)
            }
        }
        return selectedlist.map{it -> mappingPlacetoPlaceDocument(it)} //변환 된 값 넣어줘야 함.
    }

    private fun calculateDistance(x: Double, y : Double, place :Place) : Boolean {
        val theta : Double = place.y!! - x
        var distance : Double = sin(toRadians(place.x!!))*sin(toRadians(y)) + cos(toRadians((place.x!!)))*cos(toRadians((y)))*cos(toRadians(theta))

        distance = acos(distance)
        distance = toDegrees(distance)
        distance = distance*60*(1.1515)*(1.609344) //단위 : 킬로미터(km)2

        if (distance < 1000) {return true}  //1km이내인 정보만 출력해줌.
        return false
    }

    /*
    private fun calculateDistance(x: Double, y : Double, place :Place) : Boolean {
        val dLat = Math.toRadians(place.y!! - y)
        val dLng = Math.toRadians(place.x!! - x)
        val a = sin(dLat / 2).pow(2.0) + sin(dLng / 2).pow(2.0) * cos(Math.toRadians(place.y!!)) * cos(Math.toRadians(y))
        val c = 2 * asin(sqrt(a))
        println("distance " + (R * c).toInt())
        //단위 : m
        if ((R * c).toInt() < 1000) {return true}  //1km이내인 정보만 출력해줌.
        return false
    }

    fun getLocationItems(query: String, userLng : Double, userLat: Double) : List<PlaceDocument>{
        val places: List<Place> =  placeRepository.findByCategorygroupnameAndXAndY(query, userLng, userLat) //getAll로 바꾸기.
        return places.map { it ->  mappingPlacetoPlaceDocument(it) }    //이때 타입을 바꿔주면 안될듯 (return값 "엔티티" )
    }
    */


    private fun mappingPlaceDocumenttoPlace(placeDocument: PlaceDocument, category:String) : Place {
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
        place.categoryname = category
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
        placeDocument.category_name = place.categoryname
        return(placeDocument)
    }



}