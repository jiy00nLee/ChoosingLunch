package com.example.toyproject_server.PlaceDatabase


interface PlaceRepository {

    fun save(place : Place?)  //꼭 반환 X
    //fun saveAll (places : ArrayList<Place>?)  // 구현 왜 안되는지 모르겠다.

    fun findByCategorygroupnameAndXAndY(query: String, userLng : Double, userLat: Double) : List<Place> //이거 체크하기

    fun findAll() : List<Place>

    //findByplacename "Containing" 기능 쓰면 해당 문자 포한하는 데이터 출력가능.
    fun findByplacename(placeName: String): Place?

}
