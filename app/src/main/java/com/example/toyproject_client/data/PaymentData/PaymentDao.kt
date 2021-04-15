package com.example.toyproject_client.data.PaymentData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao //DTO
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPayInfoData(payinfo : PaymentEntity)

    @Query("SELECT * from paymentTable LIMIT 1")
    fun getPayInfoData() : LiveData<PaymentEntity>    //(후에 결제 내역 기능 구현시에 이거 고쳐줘야 함.)

    @Query("DELETE FROM paymentTable")
    fun deletePayInfoData() //(후에 결제 내역 기능 구현시에 이거 고쳐줘야 함.)

}