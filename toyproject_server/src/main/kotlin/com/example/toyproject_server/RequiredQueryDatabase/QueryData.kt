package com.example.toyproject_server.RequiredQueryDatabase

import javax.annotation.processing.Generated
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class QueryData {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L
    lateinit var query : String
    var lng : Double? = null
    var lat : Double? = null
    lateinit var useraddress : String
}