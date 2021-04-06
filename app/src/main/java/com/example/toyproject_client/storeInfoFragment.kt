package com.example.toyproject_client

import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.toyproject_client.data.FavoriteStoreViewmodel
import com.example.toyproject_client.data.StoreMenuItem
import com.example.toyproject_client.databinding.FragmentStoreinfoBinding
import com.example.toyproject_client.myserver.PlaceDocument
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.fragment_storeinfo.*
import kotlinx.android.synthetic.main.fragment_storeinfo.recyclerView

class storeInfoFragment : Fragment(), OnMapReadyCallback {

    //가져온 해당 가게 정보
    private val viewModel: FavoriteStoreViewmodel by viewModels()
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

    //버튼 바꿀 때 필요한 정보 정보
    var clickstate : Boolean = false  //임의의 값
    private lateinit var storeID : String



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
        clickstate = receivedItemdata!!.clickstate

        //(추가조건) 서버에서 오는 정보의 경우 엔티티에 있는 지 여부 비교를 통해 즐겨찾기 등록 여부 표현 필요.
        storeID = receivedItemdata!!.id
        viewModel.checkFavoriteStore(storeID).observe(viewLifecycleOwner){
            if (it == storeID) { //저장되어 있는 가게의 경우
                clickstate = true
            }
            changeButtonImage(clickstate)
        }

        makeMapView()
        makeView()

        like_btn.setOnClickListener {
            clickstate = (!clickstate)
            Log.d("BTN", "${clickstate}")
            changeButtonImage(clickstate)
            controlFavoriteStore(clickstate)
        }

    }
    fun changeButtonImage(clickstate : Boolean){
        if (clickstate == true) like_btn.setImageResource(R.drawable.selectedlikebutton)
        else like_btn.setImageResource(R.drawable.likebutton)
    }

    fun controlFavoriteStore(clickstate: Boolean){
        if (clickstate == true) {
            //Log.d("BTN", "가게를 등록하였습니다..")
            viewModel.insertFavoriteStore(receivedItemdata!!)
        }
        else {
            //Log.d("BTN", "가게를 삭제하였습니다..")
            viewModel.deleteFavoriteStore(receivedItemdata!!)
        }
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

    }




}