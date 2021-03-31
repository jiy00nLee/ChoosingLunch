package com.example.toyproject_server.RestAPI

import com.example.toyproject_server.Database.Place
import com.example.toyproject_server.Database.PlaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class KakaoController {

    private var placeService : PlaceService

    @Autowired
    constructor(placeService: PlaceService) {
        this.placeService = placeService
    }

    @GetMapping("/getfromKakao") //카카오 api로 지도 받아오는 서버.
    fun getFromKakao(@RequestParam("query") query: String?){
        //받아온 값 어케 처리 해주지. -> 서비스에서 해주기.
        val places = placeService.saveAll(query)
        val place_fromdb = placeService.getAll(query)
    }
    //return 값 없음.

    @GetMapping ("/getfromDatabase")
    fun getDB(query: String?) : List<Place>? {
        return placeService.getAll(query)
    }

    @GetMapping("/tonyedu") //예시 참고용용
   fun tony(query: String?) : List<Response>?{ //Null값 처리.
        //받아온 값 어케 처리 해주지.
        return listOf(Response(query), Response(query))
    }

    class Response constructor(query: String?){ //input(위도, 경도, 쿼리) 받아야댐.
        lateinit var name : String
    }
}

