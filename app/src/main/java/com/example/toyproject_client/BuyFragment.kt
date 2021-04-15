package com.example.toyproject_client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.View_Adapter.Buy_RecyclerViewAdapter
import com.example.toyproject_client.data.MenuInfoData.CartMenuItem
import com.example.toyproject_client.data.PaymentData.PaymentEntity
import com.example.toyproject_client.data.PaymentViewmodel
import com.example.toyproject_client.data.UserData.UserLocationItemData
import com.example.toyproject_client.data.UserDataViewmodel
import com.example.toyproject_client.myserver.PayInfoItem
import kotlinx.android.synthetic.main.fragment_buy.*
import java.text.DecimalFormat
import kotlin.collections.ArrayList


class BuyFragment : Fragment() {

    //필요한 정보들 뷰모델로 받아오기
    private val userviewModel: UserDataViewmodel by viewModels()
    private lateinit var userData : UserLocationItemData
    private val payviewModel : PaymentViewmodel by viewModels() //나중결제 있는 체크용
    private var checkBoolean : Boolean = false

    //번들로 받아온 정보 & 넘겨줄 정보
    private var selectedStoresMenuList : ArrayList<CartMenuItem> = arrayListOf()
    private var totalPrice : Int = 0
    private var totalstorePricetext : String = ""
    private val decform : DecimalFormat = DecimalFormat("#,###")

    // 리사이클러뷰 정보
    private lateinit var adapterBuy: Buy_RecyclerViewAdapter

    //보낼 정보
    private var sendingInfo : PayInfoItem = PayInfoItem()
    private val bundle : Bundle = Bundle()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_buy, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //유저 정보 받아와서 저장.
        userviewModel.getUserLocationData().observe(viewLifecycleOwner){
            userData = it
        }

        selectedStoresMenuList.clear()
        selectedStoresMenuList = arguments?.getParcelableArrayList<CartMenuItem>("selectedList") as ArrayList<CartMenuItem>

        selectedStoresMenuList.forEach {
            totalPrice += it.totalmenuprice
        }
        showRecyclerView()
        totalstorePricetext = decform.format(totalPrice).toString() + "원"
        totalPricetext.text = totalstorePricetext


        buybtn.setOnClickListener {
            if (!LaterSelectbtn.isChecked && !NowSelectbtn.isChecked) Toast.makeText(context, "결제 방법을 선택해주세요.", Toast.LENGTH_SHORT).show()
            else if (NowSelectbtn.isChecked){
                bundle.clear()
                bundle.putParcelableArrayList("selectedList", selectedStoresMenuList)
                bundle.putInt("totalPrice", totalPrice)
                findNavController().navigate(R.id.action_buyFragment_to_askBuyMethodDialogFragment, bundle)
            }
            else {
                bundle.clear()
                sendingInfo = PayInfoItem(userData.id, userData.username, userData.address, "", "나중결제", totalPrice, selectedStoresMenuList )
                bundle.putParcelable("sendingInfo", sendingInfo)
                findNavController().navigate(R.id.action_buyFragment_to_payResultFragment, bundle)
            }
        }

        NowSelectbtn.setOnClickListener {
            if (LaterSelectbtn.isChecked) LaterSelectbtn.isChecked = false
        }
        LaterSelectbtn.setOnClickListener {
            if (NowSelectbtn.isChecked) NowSelectbtn.isChecked = false
        }

    }

    private fun showRecyclerView() {
        adapterBuy = Buy_RecyclerViewAdapter().apply {
            received_items = selectedStoresMenuList
        }
        recyclerView.adapter = adapterBuy
    }

}