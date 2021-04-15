package com.example.toyproject_server.PayDatabase

import org.springframework.data.jpa.repository.JpaRepository


interface JpaPayRepository  : JpaRepository<Payinfo, Int>, PayRepository {


}