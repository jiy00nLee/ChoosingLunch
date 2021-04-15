package com.example.toyproject_client.data.PaymentData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "paymentTable")
data class PaymentEntity (

        @ColumnInfo(name = "userid")
        val userid : String = "",

        @ColumnInfo(name = "payaddress")
        val payaddress : String = "",

        @ColumnInfo(name = "paytime") //결제시간 저장.
        val paytime : String = "",

        @ColumnInfo(name = "payDate")
        val paycalendar : Calendar?,
        //var paycalendar : Calendar = Calendar.getInstance(), //나중결제 -> latercalendar

        @ColumnInfo(name = "paymethod")
        val paymethod : String = "",

        @ColumnInfo(name = "totalpayPrice")
        val totalpayPrice : Int = 0,

        @ColumnInfo(name="payInfo")
        val payInfo : String,

        @PrimaryKey(autoGenerate = true)
        val id : Int = 0

)
