package com.example.toyproject_server.PayDatabase

import org.springframework.data.jpa.repository.JpaRepository

interface JpaPayDetailRepository : JpaRepository<Paydetail, Long>, PayDetailRepository {

}