package com.example.toyproject_server

import com.example.toyproject_server.PlaceChineseDataBase.PlaceChinese
import com.example.toyproject_server.PlaceChineseDataBase.PlaceChineseRepository
import com.example.toyproject_server.PlaceChineseDataBase.PlaceChineseService
import com.example.toyproject_server.PlaceDatabase.PlaceService
import com.example.toyproject_server.PlaceDatabase.PlaceRepository
import com.example.toyproject_server.PlaceJapanDataBase.PlaceJapanRepository
import com.example.toyproject_server.PlaceJapanDataBase.PlaceJapanService
import com.example.toyproject_server.PlaceKoreanDataBase.PlaceKorean
import com.example.toyproject_server.PlaceKoreanDataBase.PlaceKoreanRepository
import com.example.toyproject_server.PlaceKoreanDataBase.PlaceKoreanService
import com.example.toyproject_server.PlaceWesternDataBase.PlaceWesternRepository
import com.example.toyproject_server.PlaceWesternDataBase.PlaceWesternService
import com.example.toyproject_server.RequiredQueryDatabase.QueryRepository
import com.example.toyproject_server.RequiredQueryDatabase.QueryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {

    @Autowired
    private lateinit var queryRepository: QueryRepository

    @Bean
    fun queryService() :QueryService{
        return QueryService(queryRepository)
    }


    @Autowired
    private lateinit var placeRepository : PlaceRepository

    @Bean
    fun placeService() : PlaceService {    //'PlaceService' 타입의 인스턴스인 'placeservice'를 호출하여 'Bean'에 등록해줌.
        return PlaceService(placeRepository)      // (ver5) Data JPA 용
    }


    @Autowired
    private lateinit var placekoreanRepository : PlaceKoreanRepository

    @Bean
    fun placeKoreanService() : PlaceKoreanService {
        return PlaceKoreanService(placekoreanRepository)
    }

    @Autowired
    private lateinit var placejapanRepository : PlaceJapanRepository

    @Bean
    fun placeJapanService() : PlaceJapanService {
        return PlaceJapanService(placejapanRepository)
    }

    @Autowired
    private lateinit var placechineseRepository : PlaceChineseRepository

    @Bean
    fun placeChineseService() : PlaceChineseService {
        return PlaceChineseService(placechineseRepository)
    }

    @Autowired
    private lateinit var placewesternRepository : PlaceWesternRepository

    @Bean
    fun placeWesternService() : PlaceWesternService {
        return PlaceWesternService(placewesternRepository)
    }




}