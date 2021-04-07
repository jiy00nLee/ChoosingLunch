package com.example.toyproject_server

import com.example.toyproject_server.MenuDatabase.MenuRepository
import com.example.toyproject_server.MenuDatabase.MenuService
import com.example.toyproject_server.PlaceDatabase.PlaceService
import com.example.toyproject_server.PlaceDatabase.PlaceRepository
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
    private lateinit var menuRepository : MenuRepository

    @Bean
    fun menuService() : MenuService{
        return MenuService(menuRepository)
    }


    @Autowired
    private lateinit var placeRepository : PlaceRepository

    @Bean
    fun placeService() : PlaceService {    //'PlaceService' 타입의 인스턴스인 'placeservice'를 호출하여 'Bean'에 등록해줌.
        return PlaceService(placeRepository)      // (ver5) Data JPA 용
    }


}