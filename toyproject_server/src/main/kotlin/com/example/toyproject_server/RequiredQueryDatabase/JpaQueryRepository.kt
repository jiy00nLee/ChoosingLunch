package com.example.toyproject_server.RequiredQueryDatabase

import org.springframework.data.jpa.repository.JpaRepository

interface JpaQueryRepository  : JpaRepository<QueryData, Long>, QueryRepository { //String은 'Place'의 id(pk).
    //커스텀할 DB function만

    override fun findByQueryAndLngAndLatAndUseraddress(query: String, userLng : Double, userLat: Double, userAddress: String) : QueryData?

}
