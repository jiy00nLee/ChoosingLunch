package com.example.toyproject_client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.data.PaymentViewmodel
import com.example.toyproject_client.data.UserDataViewmodel
import kotlinx.android.synthetic.main.fragment_payhistory.*
import java.text.DecimalFormat
import java.util.*

class PayHistoryFragment : DialogFragment() {
    private val userviewModel : UserDataViewmodel by viewModels()
    private val payviewModel : PaymentViewmodel by viewModels()

    private var nowcalendar  : Calendar = Calendar.getInstance()
    private var changetext : String = ""
    private val decform : DecimalFormat = DecimalFormat("#,###")
    private var timegap : Long = 0L
    private var min : Long = 0L
    private var hour : Long = 0L
    private var day  : Long = 0L
    private var msg : String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_payhistory, container, false)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        val params : ViewGroup.LayoutParams = dialog?.window?.attributes!!
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes= params as WindowManager.LayoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeView()

        dialogNobtn.setOnClickListener {  findNavController().navigate(R.id.action_payHistoryFragment_to_homeFragment) }

        dialogYesbtn.setOnClickListener {
            payviewModel.deletePayInfoData()
            findNavController().navigate(R.id.action_payHistoryFragment_to_homeFragment)
            // 후 처리 -> 서버에 등록해주어야 한다. (Entity 만들고 하기 귀찮으므로 나중계산은 서버 등록 생략 ㅎ..)
        }
        cancel_payment.setOnClickListener {
            payviewModel.deletePayInfoData()
            findNavController().navigate(R.id.action_payHistoryFragment_to_homeFragment)
        }

    }

    private fun makeView(){
        userviewModel.getUserLocationData().observe(viewLifecycleOwner){
            UserName.text = it.username
            changetext = "(" + it.id + ")"
            UserID.text = changetext
        }
        payviewModel.getPayInfoData().observe(viewLifecycleOwner){
            PayInfo.text = it.payInfo
            PayMethod.text = it.paymethod
            changetext = decform.format(it.totalpayPrice) + "원"
            PayPrice.text = changetext
            PayDate.text = it.paytime   //이거 계산 필요할 듯? -> payResult 참고하기
            countDate(it.paycalendar!!)
            showDate()
            PayLeftDate.text = msg
        }
        if (msg == "시간이 종료되었습니다."){
            AskingText.text = "기한이 만료된 주문입니다.\n 삭제하시겠습니까?"
            cancel_payment.visibility = View.GONE
            dialogNobtn.visibility = View.GONE
            dialogYesbtn.text = "삭제"    //이것도 경우의 수 나눠줘야 하는데 위에꺼랑 이어서 귀찮으므로 생략ㅎ
        }
    }

    private fun countDate(latercalendar : Calendar) {
        timegap = latercalendar.timeInMillis - nowcalendar.timeInMillis
        day = timegap/(1000*60*60*24)
        hour = (timegap%(1000*60*60*24))/(1000*60*60)
        min = (timegap%(1000*60*60*24)%(1000*60*60))/(1000*60)
    }

    private fun showDate() {
        msg = " "
        if (min <= 0L) {
            msg = "시간이 종료되었습니다."
           //payviewModel.deletePayInfoData()
        }
        else if (hour == 0L){
            msg = "(남은시간 : " + min.toString() + "분)"
        }
        else if (day == 0L){
            msg = "(남은시간 : " + hour.toString() + "시간 " + min.toString() + "분)"
        }
        else msg = "(남은시간 : " + day.toString() + "일 "+ hour.toString() + "시간 " + min.toString() + "분)"
    }


}