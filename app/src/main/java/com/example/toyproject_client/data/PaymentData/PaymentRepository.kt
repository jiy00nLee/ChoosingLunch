package com.example.toyproject_client.data.PaymentData

import androidx.lifecycle.LiveData
import com.example.toyproject_client.data.AppDatabase
import com.example.toyproject_client.data.UserData.UserDataEntity
import com.example.toyproject_client.data.UserData.UserLocationItemData
import com.example.toyproject_client.myserver.PayInfoItem


class PaymentRepository(appdatabase: AppDatabase) {

    private val paymentDao = appdatabase.PaymentDao()

    companion object {
        private var sInstance: PaymentRepository? = null

        fun getInstance(database: AppDatabase): PaymentRepository {
            return sInstance
                    ?: synchronized(this) {
                        val instance = PaymentRepository(database)
                        sInstance = instance
                        instance
                    }
        }
    }

    fun insertlaterPaymentData(payInfoItem: PaymentEntity){
        paymentDao.insertPayInfoData(payInfoItem)
    }

    fun getPayInfoData() : LiveData<PaymentEntity> {
        return paymentDao.getPayInfoData()
    }

    fun deletePayInfoData() {
        paymentDao.deletePayInfoData()
    }


    /*
    private fun mappingPayInfoItemToPaymentEntity(it : PayInfoItem) : PaymentEntity {
        val username : String = it.username
        val address : String = it.address
        val longtitude : Double = it.longtitude
        val latitude : Double = it.latitude
        return (PaymentEntity(it.userid, it.payaddress, it. ))
    }
*/

}