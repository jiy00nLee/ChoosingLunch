package com.example.toyproject_server.Database


interface PlaceRepository {
    //fun saveAll(places : List<Place>?) : List<Place>?//저장만 하면 되지 않나.\

    fun save(place : Place?)  //꼭 반환 X
    fun saveAll (places : ArrayList<Place>?)  // 구현 왜 안되는지 모르겠다.

    fun findAll() : List<Place>

    fun findByplaceName(placeName: String): Place?
    //fun findAll() : List<Place>
}
