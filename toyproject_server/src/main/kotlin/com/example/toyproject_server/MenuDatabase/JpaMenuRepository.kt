package com.example.toyproject_server.MenuDatabase

import com.example.toyproject_server.PlaceDatabase.Place
import com.example.toyproject_server.PlaceDatabase.PlaceRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaMenuRepository : JpaRepository<Menu, String>, MenuRepository {

    override fun findByStoreid(storeid : String)


}