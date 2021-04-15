package com.example.toyproject_server.PayDatabase

import javax.persistence.*

@Entity
class Paydetail {
    @Id
    @Column(name="paydetailinfo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long = 0
    var storename : String = ""
    var menuinfotext : String = ""
    var totalmenuprice : Int = 0
    var storeid : String = ""
    var menupriceinfotext : String = ""

    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name="payinfo_id", referencedColumnName = "payinfo_id")
    var payinfo : Payinfo = Payinfo()
    //lateinit var userid: String
}