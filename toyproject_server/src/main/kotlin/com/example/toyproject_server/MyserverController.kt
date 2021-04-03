package com.example.toyproject_server

import com.example.toyproject_server.PlaceChineseDataBase.PlaceChineseService
import com.example.toyproject_server.PlaceDatabase.PlaceService
import com.example.toyproject_server.PlaceJapanDataBase.PlaceJapanService
import com.example.toyproject_server.PlaceKoreanDataBase.PlaceKoreanService
import com.example.toyproject_server.PlaceWesternDataBase.PlaceWestern
import com.example.toyproject_server.PlaceWesternDataBase.PlaceWesternService
import com.example.toyproject_server.RequiredQueryDatabase.QueryData
import com.example.toyproject_server.RequiredQueryDatabase.QueryService
import com.example.toyproject_server.RestAPI.PlaceDocument
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class MyserverController {

    @Autowired
    private lateinit var queryService : QueryService

    @Autowired
    private lateinit var placeService : PlaceService
    @Autowired
    private lateinit var placekoreanService : PlaceKoreanService
    @Autowired
    private lateinit var placejapanService : PlaceJapanService
    @Autowired
    private lateinit var placechineseService : PlaceChineseService
    @Autowired
    private lateinit var placewesternService : PlaceWesternService



    @GetMapping("/myServerKakao") //카카오 api로 지도 받아오는 서버.
    fun getFromKakao(@RequestParam("query") query: String,
                     @RequestParam("x") userLng: Double, @RequestParam("y") userLat: Double){

        //여기서 DB폭 늘려야 함.
        //카카오 서버로부터 데이터 찾아서 디비에 넣어주기.
        val querylist : MutableList<String> = mutableListOf()
        if (query == "한식") KoreaService(userLng, userLat)
        else if (query == "일식") JapanService(userLng, userLat)
        else if (query == "중식") ChineseService(userLng, userLat)
        else if (query == "양식") WesternService(userLng, userLat)
        else Service(userLng, userLat)

    }

    private fun KoreaService( userLng: Double, userLat: Double){
        val querylist : MutableList<String> = mutableListOf()
        querylist.add("한식")
        querylist.add("한정식")
        querylist.add("가정식")
        querylist.add("korean")
        querylist.add("집밥")

        for (query in querylist){
            placekoreanService.saveAll(query, userLng, userLat)
        }
    }
    private fun JapanService( userLng: Double, userLat: Double){
        val querylist : MutableList<String> = mutableListOf()
        querylist.add("일식")
        querylist.add("일본가정식")
        querylist.add("japanese")
        querylist.add("일본")

        for (query in querylist){
            placejapanService.saveAll(query, userLng, userLat)
        }
    }
    private fun ChineseService( userLng: Double, userLat: Double){
        val querylist : MutableList<String> = mutableListOf()
        querylist.add("중식")
        querylist.add("중국집")
        querylist.add("중화요리")
        querylist.add("중국")
        querylist.add("Chinese")

        for (query in querylist){
            placechineseService.saveAll(query, userLng, userLat)
        }
    }

    private fun WesternService( userLng: Double, userLat: Double){
        val querylist : MutableList<String> = mutableListOf()
        querylist.add("양식")
        querylist.add("파스타")
        querylist.add("레스토랑")

        for (query in querylist){
            placewesternService.saveAll(query, userLng, userLat)
        }
    }

    private fun Service( userLng: Double, userLat: Double){
        val querylist : MutableList<String> = mutableListOf()

        querylist.add("음식점")
        querylist.add("맛집")
        querylist.add("가정식")
        querylist.add("집밥")

        for (query in querylist){
            placeService.saveAll(query, userLng, userLat)
        }
    }


    @GetMapping ("/myServerDatabase")
    fun getDB(@RequestParam("query") query: String,
              @RequestParam("x") userLng: Double, @RequestParam("y") userLat: Double) : List<PlaceDocument> {

        val foundquery : QueryData? = queryService.findQuery(query, userLng, userLat) //query 찾은 적 있었는지 여부 찾기.

        if (foundquery == null ){//이전에 찾은적이 없는 정보인 경우.
            println("Its not in db!")
            getFromKakao(query, userLng, userLat)
            queryService.saveQuery(query, userLng, userLat)
        }

        lateinit var resultStorelistdata : List<PlaceDocument>
        if (query == "한식")  resultStorelistdata = placekoreanService.getAll(query, userLng, userLat)
        else if (query == "일식") resultStorelistdata = placejapanService.getAll(query, userLng, userLat)
        else if (query == "중식") resultStorelistdata = placechineseService.getAll(query, userLng, userLat)
        else if (query == "양식") resultStorelistdata = placewesternService.getAll(query, userLng, userLat)
        else resultStorelistdata = placeService.getAll(query, userLng, userLat)

        return resultStorelistdata
    }


    /*
    @GetMapping("/tonyedu") //예시 참고용용
   fun tony(query: String?) : List<Response>?{ //Null값 처리.
        //받아온 값 어케 처리 해주지.
        return listOf(Response(query), Response(query))
    }

    class Response constructor(query: String?){ //input(위도, 경도, 쿼리) 받아야댐.
        lateinit var name : String
    }*/
}

