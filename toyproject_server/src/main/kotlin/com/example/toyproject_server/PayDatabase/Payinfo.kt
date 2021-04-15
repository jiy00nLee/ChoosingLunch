package com.example.toyproject_server.PayDatabase

import javax.persistence.*


@Entity
class Payinfo  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payinfo_id")
    val id : Long = 0
    var userid: String = ""
    var username : String = ""
    var payaddress : String = ""
    var paydaytime : String = ""
    var totalpayprice : Int =0
    var paymethod : String = ""

    @OneToMany(mappedBy = "payinfo", fetch=FetchType.EAGER, cascade = arrayOf(CascadeType.ALL), orphanRemoval = true) //
    //@JoinColumn(name = "paydetailinfo_id")
    val paydetaildata : List<Paydetail> = ArrayList<Paydetail>()

}