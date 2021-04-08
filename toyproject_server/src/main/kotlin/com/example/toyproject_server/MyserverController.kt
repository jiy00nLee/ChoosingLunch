package com.example.toyproject_server

import com.example.toyproject_server.MenuDatabase.StoreMenuItem
import com.example.toyproject_server.MenuDatabase.MenuService
import com.example.toyproject_server.PlaceDatabase.PlaceService
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


    @GetMapping("/myServerKakao") //카카오 api로 지도 받아오는 서버.
    fun getFromKakao(@RequestParam("query") query: String,
                     @RequestParam("x") userLng: Double, @RequestParam("y") userLat: Double){

        //여기서 DB폭 늘려야 함.
        //카카오 서버로부터 데이터 찾아서 디비에 넣어주기. //DB에 가게별 음식들도 등록을 해주어야 한다.
        val final_storelist_ids = Service(userLng, userLat, query)
        menuService.makeMenus(final_storelist_ids)

    }


    private fun Service(userLng: Double, userLat: Double, query : String) : MutableList<String>{
        val querylist : MutableList<String> = mutableListOf()
        val storelist_ids : MutableList<String> = mutableListOf()

        if (query == "한식"){
            querylist.add("한식")
            querylist.add("한정식")
            querylist.add("가정식")
            querylist.add("korean")
            querylist.add("집밥")
        }
        else if (query == "일식"){
            querylist.add("일식")
            querylist.add("일본가정식")
            querylist.add("japanese")
            querylist.add("일본")
        }
        else if (query == "중식"){
            querylist.add("중식")
            querylist.add("중국집")
            querylist.add("중화요리")
            querylist.add("중국")
            querylist.add("Chinese")
        }
        else if (query == "양식"){
            querylist.add("양식")
            querylist.add("파스타")
            querylist.add("레스토랑")
        }
        else return storelist_ids  //query == "음식점"일 경우 저장하지않는다.

        for ( i in querylist){
            placeService.saveAll( i, userLng, userLat, query)?.let { storelist_ids.addAll(it) }
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

        if (query == "음식점")  resultStorelistdata = placeService.getAll(userLng, userLat)
        else resultStorelistdata = placeService.getCategoryAll(query, userLng, userLat)
        println("쿼리= " + query + " 가져온 정보 =  " + resultStorelistdata + " !!!!")
        return resultStorelistdata
    }

    @GetMapping("/myServerMenuDatabase")
    fun getMenuDB(@RequestParam("storeID") storeID : String) : List<StoreMenuItem>? {
        val foundmenulist : List<StoreMenuItem>? = menuService.findStoreID(storeID)
        return foundmenulist
    }


}

