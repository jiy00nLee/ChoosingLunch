package com.example.toyproject_client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.view.HomeFragViewmodel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.userlocation

class HomeFragment : Fragment() {
    private val viewModel: HomeFragViewmodel by viewModels()

    //사용자 정보 변수들.
    private lateinit var useraddress: String
    private var userLat: Double = 0.0
    private var userLng: Double = 0.0


    //private var near: LatLng? = null //좌표인듯.
    //private lateinit var select_mode  -> 클릭여부에 따른 모드 변경 추가하기.
    private lateinit var adapterStore: Store_RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        //(가입시) 이미 정보가 있다는 가정하이다.
        viewModel.getUserLocationData().observe(viewLifecycleOwner) {
            useraddress = it.address
            userLat = it.latitude
            userLng = it.longtitude
            userlocation.text = useraddress //주소같은 경우 xml로 표현해주기.
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //사용자 위치등록하기.
        location_btn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_insertLocationInfoFragment)
        }

        all_btn.setOnClickListener {
            viewModel.getStoreList("음식점", userLng, userLat)
            showRecyclerView()
        }

        korean_btn.setOnClickListener {
            viewModel.getStoreList("한식", userLng, userLat)
            showRecyclerView()
        }

        japanese_btn.setOnClickListener {
            viewModel.getStoreList("일식", userLng, userLat)
            showRecyclerView()
        }
        chinese_btn.setOnClickListener {
            viewModel.getStoreList("중식", userLng, userLat)
            showRecyclerView()
        }
        west_btn.setOnClickListener {
            viewModel.getStoreList("양식", userLng, userLat)
            showRecyclerView()
        }
    }


    private fun showRecyclerView() {
        viewModel.livedata_resultplaces.observe(viewLifecycleOwner){ resultplaces ->

            if (resultplaces!=null){
                adapterStore = Store_RecyclerViewAdapter(resultplaces!!) { placeDocument ->
                    Log.d("Checking!!", "${placeDocument}")
                    val bundle = Bundle()
                    bundle?.putParcelable("selectedStore", placeDocument)
                    findNavController().navigate(
                            R.id.action_homeFragment_to_storeInfoFragment,
                            bundle
                    )
                }//.apply { rc_storeItems = resultplaces!! }
                recyclerView.adapter = adapterStore
            }
        }
    }


}





