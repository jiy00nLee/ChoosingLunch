package com.example.toyproject_server.RequiredQueryDatabase

interface QueryRepository {
    fun findByQueryAndLngAndLatAndUseraddress(query: String, userLng : Double, userLat: Double, userAddress: String) : QueryData?
    fun save(querydata : QueryData)
}