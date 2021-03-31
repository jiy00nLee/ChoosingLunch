package com.example.toyproject_server

import com.example.toyproject_server.Database.PlaceRepository
import com.example.toyproject_server.Database.PlaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {

    private var placeRepository : PlaceRepository

    @Autowired //SpringDataJpa에서 이미 @Bean에 등록해두었으므로 걔를 @Autowired로 받아와서 엮어줌.
    constructor(placeRepository : PlaceRepository){
        this.placeRepository = placeRepository       // (ver5) Data JPA 용
    }

    @Bean
        fun placeService() : PlaceService {    //'PlaceService' 타입의 인스턴스인 'placeservice'를 호출하여 'Bean'에 등록해줌.
        return PlaceService(placeRepository)      // (ver5) Data JPA 용
    }



}