package com.example.toyproject_client.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.toyproject_client.data.MenuInfoData.NenuInfoRepository
import com.example.toyproject_client.data.PaymentData.PaymentEntity
import com.example.toyproject_client.data.PaymentData.PaymentRepository
import com.example.toyproject_client.myserver.MyserverRepository
import com.example.toyproject_client.myserver.PayInfoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentViewmodel(application: Application) : AndroidViewModel(application) {

    private val myserverRepository = MyserverRepository.getInstance()
    private val menuInfoRepository =  NenuInfoRepository.getInstance(AppDatabase.getDatabase(application, viewModelScope))
    private val paymentRepository = PaymentRepository.getInstance(AppDatabase.getDatabase(application, viewModelScope))

    fun putPayInfoData(payInfoItem: PayInfoItem)  { //정석대로이면 분리시켜주어야 한다. /  = viewModelScope.launch(Dispatchers.IO)
        myserverRepository.putPayInfoData(payInfoItem)
    }

    fun deleteMenuInfoListByStore(storeID: String) = viewModelScope.launch(Dispatchers.IO){
        menuInfoRepository.deleteMenuInfoListByStore(storeID)
    }

    fun insertlaterPaymentData(payInfoItem: PaymentEntity) = viewModelScope.launch(Dispatchers.IO){
        paymentRepository.insertlaterPaymentData(payInfoItem)
    }

    fun getPayInfoData() : LiveData<PaymentEntity>{
        return paymentRepository.getPayInfoData()
    }

    fun deletePayInfoData() = viewModelScope.launch(Dispatchers.IO){
        paymentRepository.deletePayInfoData()
    }


    //fun getPayInfoData() : LiveData<PaymentEntity>

}

