package com.example.toyproject_server.PayDatabase


interface PayRepository {

    fun save(paydetail : Payinfo?)

}