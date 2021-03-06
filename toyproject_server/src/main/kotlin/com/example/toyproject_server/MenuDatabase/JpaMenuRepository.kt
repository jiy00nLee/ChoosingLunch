package com.example.toyproject_server.MenuDatabase

import org.springframework.data.jpa.repository.JpaRepository

interface JpaMenuRepository : JpaRepository<Menu, String>, MenuRepository {

    override fun findByStoreid(storeid : String) : List<Menu>?


}