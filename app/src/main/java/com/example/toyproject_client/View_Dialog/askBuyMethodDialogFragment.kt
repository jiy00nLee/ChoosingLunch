package com.example.toyproject_client.View_Dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.R
import com.example.toyproject_client.data.MenuInfoData.CartMenuItem
import com.example.toyproject_client.data.UserData.UserLocationItemData
import com.example.toyproject_client.data.UserDataViewmodel
import com.example.toyproject_client.myserver.PayInfoItem
import kotlinx.android.synthetic.main.fragment_askbuymethoddialog.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class askBuyMethodDialogFragment : DialogFragment() {

    //필요한 정보들 뷰모델로 받아오기
    private val userviewModel: UserDataViewmodel by viewModels()
    private lateinit var userData : UserLocationItemData

    //받아온 정보
    private var selectedStoresMenuList : ArrayList<CartMenuItem> = arrayListOf()
    private var totalPrice : Int = 0
    private var totalstorePricetext : String = ""   //(번들)

    //보낼 정보
    private var sendingInfo : PayInfoItem = PayInfoItem()
    private var paymethod : String = ""
    private val bundle : Bundle = Bundle()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_askbuymethoddialog, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedStoresMenuList.clear()
        selectedStoresMenuList = arguments?.getParcelableArrayList<CartMenuItem>("selectedList") as ArrayList<CartMenuItem>
        totalPrice = arguments?.getInt("totalPrice")!!

        dialogBuybtn.text = totalstorePricetext + " 결제하기"

        userviewModel.getUserLocationData().observe(viewLifecycleOwner){
            userData = it
        }

        dialogBuybtn.setOnClickListener {
            if (!SelectCardbtn.isChecked && !SelectAccountbtn.isChecked && !SelectVirtualAccountbtn.isChecked) Toast.makeText(context, "결제 방법을 선택해주세요.", Toast.LENGTH_SHORT).show()
            else {
                checkPaymethod()
                sendingInfo = PayInfoItem(userData.id, userData.username, userData.address, "", paymethod, totalPrice, selectedStoresMenuList )
                bundle.putParcelable("sendingInfo", sendingInfo)
                findNavController().navigate(R.id.action_askBuyMethodDialogFragment_to_payResultFragment, bundle)
            }
        }

        SelectCardbtn.setOnClickListener {
            if (SelectAccountbtn.isChecked) SelectAccountbtn.isChecked = false
            else if (SelectVirtualAccountbtn.isChecked) SelectVirtualAccountbtn.isChecked = false
        }

        SelectAccountbtn.setOnClickListener {
            if (SelectVirtualAccountbtn.isChecked) SelectVirtualAccountbtn.isChecked = false
            else if (SelectCardbtn.isChecked) SelectCardbtn.isChecked = false
        }

        SelectVirtualAccountbtn.setOnClickListener {
            if (SelectAccountbtn.isChecked) SelectAccountbtn.isChecked = false
            else if (SelectCardbtn.isChecked) SelectCardbtn.isChecked = false
        }

    }

    private fun checkPaymethod(){
        if (SelectCardbtn.isChecked) paymethod = "카드결제"
        else if (SelectAccountbtn.isChecked) paymethod = "실시간 계좌이체"
        else paymethod = "가상계좌"
    }

}