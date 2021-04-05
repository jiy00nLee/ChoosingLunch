package com.example.toyproject_server.RequiredQueryDatabase

import com.example.toyproject_server.PlaceDatabase.PlaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class QueryService {

    private val queryRepository : QueryRepository

    @Autowired
    constructor(queryRepository : QueryRepository) {
        this.queryRepository = queryRepository
    }

    fun findQuery(query: String, userLng : Double, userLat: Double, userAddress:String) : QueryData? {
        return queryRepository.findByQueryAndLngAndLatAndUseraddress(query, userLng, userLat, userAddress)
    }
    fun saveQuery(query: String, userLng : Double, userLat: Double, userAddress:String){
        val transed_queryData : QueryData = mappingDatastoQueryData(query, userLng, userLat,userAddress)
        queryRepository.save(transed_queryData)
    }

    private fun mappingDatastoQueryData(query: String, userLng : Double, userLat: Double, userAddress:String) : QueryData{
        val queryData : QueryData = QueryData()
        //이 경우,  id는 autogenerate이므로 지정해줄 필요 X.
        queryData.query = query
        queryData.lng = userLng
        queryData.lat = userLat
        queryData.useraddress = userAddress
        return queryData
    }


}