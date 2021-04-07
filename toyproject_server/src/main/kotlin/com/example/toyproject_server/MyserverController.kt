package com.example.toyproject_server

import com.example.toyproject_server.MenuDatabase.StoreMenuItem
import com.example.toyproject_server.MenuDatabase.MenuService
import com.example.toyproject_server.PlaceChineseDataBase.PlaceChineseService
import com.example.toyproject_server.PlaceDatabase.PlaceService
import com.example.toyproject_server.PlaceJapanDataBase.PlaceJapanService
import com.example.toyproject_server.PlaceKoreanDataBase.PlaceKoreanService
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
    private lateinit var menuService: MenuService

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
        //카카오 서버로부터 데이터 찾아서 디비에 넣어주기. //DB에 가게별 음식들도 등록을 해주어야 한다.
        if (query == "한식") {
            val final_storelist_ids = KoreaService(userLng, userLat)  // 1) DB에 가게 등록
            menuService.makeMenus(final_storelist_ids)// 자동화 에바 (원래라면 API에서 주어야 함)    // 2) 가게마다 메뉴 등록
        }
        else if (query == "일식") {
            val final_storelist_ids = JapanService(userLng, userLat)
            menuService.makeMenus(final_storelist_ids)
        }
        else if (query == "중식") {
            val final_storelist_ids = ChineseService(userLng, userLat)
            menuService.makeMenus(final_storelist_ids)
        }
        else if (query == "양식") {
            val final_storelist_ids = WesternService(userLng, userLat)
            menuService.makeMenus(final_storelist_ids)
        }
        else {
            val final_storelist_ids = Service(userLng, userLat)
            menuService.makeMenus(final_storelist_ids)
        }

    }

    private fun KoreaService( userLng: Double, userLat: Double) : MutableList<String>{
        val querylist : MutableList<String> = mutableListOf()
        querylist.add("한식")
        querylist.add("한정식")
        querylist.add("가정식")
        querylist.add("korean")
        querylist.add("집밥")

        val storelist_ids : MutableList<String> = mutableListOf()
        for (query in querylist){
            placekoreanService.saveAll(query, userLng, userLat)?.let { storelist_ids.addAll(it) }
        }
        return storelist_ids
    }

    private fun JapanService( userLng: Double, userLat: Double) : MutableList<String> {
        val querylist : MutableList<String> = mutableListOf()
        querylist.add("일식")
        querylist.add("일본가정식")
        querylist.add("japanese")
        querylist.add("일본")

        val storelist_ids : MutableList<String> = mutableListOf()
        for (query in querylist){
            placekoreanService.saveAll(query, userLng, userLat)?.let { storelist_ids.addAll(it) }
        }
        return storelist_ids
    }

    private fun ChineseService( userLng: Double, userLat: Double) : MutableList<String> {
        val querylist : MutableList<String> = mutableListOf()
        querylist.add("중식")
        querylist.add("중국집")
        querylist.add("중화요리")
        querylist.add("중국")
        querylist.add("Chinese")

        val storelist_ids : MutableList<String> = mutableListOf()
        for (query in querylist){
            placekoreanService.saveAll(query, userLng, userLat)?.let { storelist_ids.addAll(it) }
        }
        return storelist_ids
    }

    private fun WesternService( userLng: Double, userLat: Double) : MutableList<String> {
        val querylist : MutableList<String> = mutableListOf()
        querylist.add("양식")
        querylist.add("파스타")
        querylist.add("레스토랑")

        val storelist_ids : MutableList<String> = mutableListOf()
        for (query in querylist){
            placekoreanService.saveAll(query, userLng, userLat)?.let { storelist_ids.addAll(it) }
        }
        return storelist_ids
    }

    private fun Service( userLng: Double, userLat: Double) : MutableList<String> {
        val querylist : MutableList<String> = mutableListOf()

        querylist.add("음식점")
        querylist.add("맛집")
        querylist.add("가정식")
        querylist.add("집밥")

        val storelist_ids : MutableList<String> = mutableListOf()
        for (query in querylist){
            placeService.saveAll(query, userLng, userLat)?.let { storelist_ids.addAll(it) }
        }
        return storelist_ids
    }


    @GetMapping ("/myServerPlaceDatabase")
    fun getPlaceDB(@RequestParam("query") query: String,
              @RequestParam("x") userLng: Double, @RequestParam("y") userLat: Double,
            @RequestParam("userAddress") userAddress : String) : List<PlaceDocument> {

        val foundquery : QueryData? = queryService.findQuery(query, userLng, userLat,userAddress) //query 찾은 적 있었는지 여부 찾기.

        if (foundquery == null ){//이전에 찾은적이 없는 정보인 경우.(등록해줌)
            println("Its not in db!")
            getFromKakao(query, userLng, userLat) //DB에 가게별 음식들도 등록을 해주어야 한다.
            queryService.saveQuery(query, userLng, userLat, userAddress)
        }

        lateinit var resultStorelistdata : List<PlaceDocument>
        if (query == "한식")  resultStorelistdata = placekoreanService.getAll(query, userLng, userLat)
        else if (query == "일식") resultStorelistdata = placejapanService.getAll(query, userLng, userLat)
        else if (query == "중식") resultStorelistdata = placechineseService.getAll(query, userLng, userLat)
        else if (query == "양식") resultStorelistdata = placewesternService.getAll(query, userLng, userLat)
        else resultStorelistdata = placeService.getAll(query, userLng, userLat)

        return resultStorelistdata
    }

    @GetMapping("/myServerMenuDatabase")
    fun getMenuDB(@RequestParam("storeID") storeID : String) : List<StoreMenuItem>? {
        val foundmenulist : List<StoreMenuItem>? = menuService.findStoreID(storeID)
        return foundmenulist
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

