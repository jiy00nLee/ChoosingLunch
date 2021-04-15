package com.example.toyproject_client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.data.PaymentData.PaymentEntity
import com.example.toyproject_client.data.PaymentViewmodel
import com.example.toyproject_client.myserver.PayInfoItem
import kotlinx.android.synthetic.main.fragment_payresult.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class PayResultFragment : Fragment() {

    //필요한 정보들 가져오기.
    private val payviewModel: PaymentViewmodel by viewModels()
    private var payResultInfo : PayInfoItem = PayInfoItem() //서버에 보낼 정보를 번들로 받아옴.
    private var changetext : String = ""
    private val decform : DecimalFormat = DecimalFormat("#,###")

    //시간 계산용 변수들
    private var nowcalendar  : Calendar = Calendar.getInstance()
    private var nowTime  : Date = nowcalendar.time
    private var latercalendar  : Calendar = Calendar.getInstance()
    private var dateFormat : String = ""

    //나중결제 정보의 경우 저장해주어야 함.


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_payresult, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(this) {    //BackController -> 홈화면 이동
            findNavController().navigate(R.id.action_payResultFragment_to_homeFragment )  }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        payResultInfo = PayInfoItem()
        payResultInfo = arguments?.getParcelable<PayInfoItem>("sendingInfo")!!

        if (payResultInfo.paymethod == "나중결제") {
            latercalendar.add(Calendar.DATE, +7)
        }

        dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(nowTime)
        payResultInfo.paydaytime = dateFormat


        showView()
        sendPayDatatoServer()

        movetoHomebtn.setOnClickListener { findNavController().navigate(R.id.action_payResultFragment_to_homeFragment )  }
    }

    private fun sendPayDatatoServer(){
        //현재 시간 가져와서 계산시간정보로 넣어줌.

        if (payResultInfo.paymethod == "나중결제") {
            payviewModel.insertlaterPaymentData(PaymentEntity(payResultInfo.userid, payResultInfo.payaddress, dateFormat,
                    latercalendar, payResultInfo.paymethod, payResultInfo.totalpayPrice, changetext))
        }
        else payviewModel.putPayInfoData(payResultInfo)

        //장바구니에서 해당 정보들 삭제해 줘야함.
        payResultInfo.paydetailData.forEach {
            payviewModel.deleteMenuInfoListByStore(it.storeid)
        }

    }

    private fun showView(){
        UserName.text = payResultInfo.username
        changetext= "(" + payResultInfo.userid + ")"
        UserID.text = changetext
        PayMethod.text = payResultInfo.paymethod
        changetext = decform.format(payResultInfo.totalpayPrice).toString() + "원"
        PayPrice.text = changetext
        if(payResultInfo.paymethod == "나중결제") {
            PayDate.text = "1주일 이내 결제를 완료해주세요."
        }
        else PayDate.text = payResultInfo.paydaytime
        if (payResultInfo.paydetailData.size == 1){  changetext = payResultInfo.paydetailData[0].storename  }
        else changetext = payResultInfo.paydetailData[0].storename + " 외 " + (payResultInfo.paydetailData.size-1).toString() + "개"
        PayInfo.text = changetext
    }


}