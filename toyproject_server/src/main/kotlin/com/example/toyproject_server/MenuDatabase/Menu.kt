package com.example.toyproject_server.MenuDatabase

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(MenuProductPK::class)
class Menu {
    @Id
    lateinit var storeid: String
    @Id
    lateinit var name : String
    var price : Int = 0
}