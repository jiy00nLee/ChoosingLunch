package com.example.toyproject_client

import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.toyproject_client.data.storeMenu.StoreMenuItem
import com.example.toyproject_client.databinding.FragmentStoreinfoBinding
import com.example.toyproject_client.myserver.PlaceDocument
import com.example.toyproject_client.view.HomeFragViewmodel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.fragment_storeinfo.*
import kotlinx.android.synthetic.main.fragment_storeinfo.recyclerView

class storeInfoFragment : Fragment(), OnMapReadyCallback {

    //가져온 해당 가게 정보
    private val viewModel: HomeFragViewmodel by viewModels()
    private lateinit var binding : FragmentStoreinfoBinding
    var receivedItemdata : PlaceDocument? = null

    // 리사이클러뷰 정보
    private lateinit var adapterStore: Menu_RecyclerViewAdapter
    var storeMenues : List<StoreMenuItem> = listOf()


    //Map 띄우기 위한 정보
    private var locationManager : LocationManager?= null //LocationManager
    private var connectionManager : ConnectivityManager?= null //WifiManager
    private var locationSource : FusedLocationSource ? = null
    private var naverMap: NaverMap? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_storeinfo, container, false)
        val rootView = binding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receivedItemdata = arguments?.getParcelable("selectedStore")
        makeMapView()
        makeView()

    }



    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onMapReady(naverMap: NaverMap) {
        val StoreLatLng = LatLng(receivedItemdata?.y!!, receivedItemdata?.x!!)
        val cameraposition : CameraPosition = CameraPosition(StoreLatLng, 17.0)
        naverMap.cameraPosition = cameraposition
        this.naverMap = naverMap

        //가게 위치 지도에 마커로 표현.
        val marker = Marker()
        marker.position = StoreLatLng
        marker.captionText = receivedItemdata!!.place_name
        marker.map = naverMap

    }

    private fun makeMapView(){
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.selectedstore_map) as MapFragment?
                ?: MapFragment.newInstance().also {     //mapFragment가 null일 경우.(새로 생성)
                    fm.beginTransaction().add(R.id.selectedstore_map, it).commit()  }

        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, InsertLocationInfoFragment.LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun makeView(){
        binding.storeItem = receivedItemdata

        //해당 가게의 음식들을 가져와야 한다. (메뉴 표현)
        adapterStore = Menu_RecyclerViewAdapter(storeMenues)
        recyclerView.adapter = adapterStore

        like.setOnClickListener {
            //DB에 테이블 등록하기.
        }

    }




}