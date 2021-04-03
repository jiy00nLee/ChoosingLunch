package com.example.toyproject_server.RequiredQueryDatabase

interface QueryRepository {
    fun findByQueryAndLngAndLat(query: String, userLng : Double, userLat: Double) : QueryData?
    fun save(querydata : QueryData)
}