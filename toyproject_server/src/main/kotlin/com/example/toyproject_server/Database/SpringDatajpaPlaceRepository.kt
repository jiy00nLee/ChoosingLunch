package com.example.toyproject_server.Database

import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface SpringDatajpaPlaceRepository : JpaRepository<Place,String>, PlaceRepository{ //String은 'Place'의 id(pk).
    //커스텀할 DB function만

}