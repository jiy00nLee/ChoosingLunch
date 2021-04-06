package com.example.toyproject_server.MenuDatabase

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Menu {
    @Id
    lateinit var storeid: String
    lateinit var name : String
    var price : Int = 0
}