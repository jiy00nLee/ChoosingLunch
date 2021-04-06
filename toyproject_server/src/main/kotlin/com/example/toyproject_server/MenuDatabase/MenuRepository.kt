package com.example.toyproject_server.MenuDatabase

interface MenuRepository {
    fun save(menu : Menu?)

    fun findByStoreid(storeid : String)

    //fun findAll() : List<Menu>

}