package com.example.toyproject_server.PayDatabase

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class PayService {

    private val payRepository : PayRepository
    private val payDetailRepository : PayDetailRepository

    @Autowired
    constructor(payRepository : PayRepository, payDetailRepository: PayDetailRepository){
        this.payRepository = payRepository
        this.payDetailRepository = payDetailRepository
    }

    fun insertpayInfo(body : PayInfoItem){
        val Payinfo = mappingPayInfoItemtoPayInfo(body)
        //payRepository.
        payRepository.save(Payinfo)
        body.paydetailData.forEach { paydetailData->
            payDetailRepository.save(mappingPayInfoItemtoPayInfoDetail(paydetailData, Payinfo))  }

    }

    private fun mappingPayInfoItemtoPayInfo(payInfoItem: PayInfoItem) : Payinfo {
        val payInfo : Payinfo = Payinfo()
        payInfo.userid = payInfoItem.userid
        payInfo.username = payInfoItem.username
        payInfo.payaddress = payInfoItem.payaddress
        payInfo.paydaytime = payInfoItem.paydaytime
        payInfo.totalpayprice = payInfoItem.totalpayPrice
        payInfo.paymethod = payInfoItem.paymethod
        return(payInfo)
    }

    private fun mappingPayInfoItemtoPayInfoDetail(paydetailData: PayDetailItem, payinfo: Payinfo): Paydetail {
        val paydetailInfo : Paydetail = Paydetail()
        paydetailInfo.storeid = paydetailData.storeid
        paydetailInfo.storename = paydetailData.storename
        paydetailInfo.menuinfotext = paydetailData.menuinfotext.trim().replace("\n", "/")
        paydetailInfo.menupriceinfotext = paydetailData.menupriceinfotext.trim().replace("\n", "/")
        paydetailInfo.totalmenuprice = paydetailData.totalmenuprice
        paydetailInfo.payinfo = payinfo
        return(paydetailInfo)
    }
}