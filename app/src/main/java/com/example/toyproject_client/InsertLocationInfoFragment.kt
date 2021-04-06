package com.example.toyproject_client

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.data.UserDataViewmodel
import com.example.toyproject_client.data.UserData.UserLocationItemData
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.fragment_insertlocationinfo.*
import java.util.*

class InsertLocationInfoFragment : Fragment(), OnMapReadyCallback {
    private val viewModel: UserDataViewmodel by viewModels()

    private var locationManager : LocationManager?= null //LocationManager
    private var connectionManager : ConnectivityManager?= null //WifiManager
    private var locationSource : FusedLocationSource ? = null
    private var naverMap: NaverMap? = null


    companion object {
        private const val TAG = "Map"
        const val LOCATION_PERMISSION_REQUEST_CODE = 1000 //리퀘타임_상수
        private val PERMISSIONS = arrayOf<String>(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    //주소 변환용 변수들.
    private var nowLat : Double = 35.157662
    private var nowLng : Double = 129.059111
    private var addresslist: List<Address>? = null
    private var address : String? = null

    //사용자 관련 변수들.
    private lateinit var username : String
    private lateinit var userlocation : String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_insertlocationinfo, container, false)
        //(가입시) 이미 정보가 있다는 가정하이다.
        viewModel.getUserLocationData().observe(viewLifecycleOwner){
            username = it.username
            userlocation = it.address
            address = userlocation
            userName.text = username
            userLocation.text = userlocation
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeMapView()

        //사용자 위치 정보 서버에 입력하기.
        inputlocation_btn.setOnClickListener {
            //find() -> 서버에 현재 사용자 위치입력 후 관련된 쿼리 생성해서 데이터 뽑아서 디비에 저장해야함. (해야할 것!)
            if (address != null){
                viewModel.insertUserLocationData(UserLocationItemData(username,  address.toString(), nowLat, nowLng))  //로그인 구현시에 바꾸어 주어야 함.!!!!!
                findNavController().navigate(R.id.action_insertLocationInfoFragment_to_homeFragment)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // request code와 권한획득 여부 확인
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) { //위치 허용 O (!= 위치 on이랑 다름)
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                naverMap?.locationTrackingMode = LocationTrackingMode.Follow
            }
            else naverMap?.locationTrackingMode = LocationTrackingMode.None
            return
        }
    }

    private fun makeMapView(){
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
                ?: MapFragment.newInstance().also {     //mapFragment가 null일 경우.(새로 생성)
                    fm.beginTransaction().add(R.id.map, it).commit()  }

        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, InsertLocationInfoFragment.LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onMapReady(naverMap: NaverMap) {   //이게 oncreated같은 개념 맞나? -> 그래서 여기 버튼 onclick 넣음

        this.naverMap = naverMap
        naverMap!!.locationSource = locationSource // 초기 위치 설정(주의:locationSource!= latitude)
        naverMap!!.locationTrackingMode = LocationTrackingMode.Follow     //권한 허용시(카메라 따라가게 모드 설정 o)
        this.naverMap = naverMap

        requestPermissions(PERMISSIONS,LOCATION_PERMISSION_REQUEST_CODE  )
        Toast.makeText(context, "사용자 위치를 찾고 있는 중입니다.", Toast.LENGTH_SHORT).show()

        //현재위치 찾아주는 버튼이다.
        finduserslocation_btn.setOnClickListener{
            findUsersLocation(naverMap)
        }

        //사용자의 위치가 변경될 경우 호출되는 콜백 메서드
        naverMap.addOnLocationChangeListener { location ->
            nowLat = location.latitude
            nowLng = location.longitude
        }
    }

    private fun CheckServiceState() : Boolean {
        if (!locationManager!!.isLocationEnabled && connectionManager!!.activeNetwork==null){  //GPS나 네트워크가 Off 일때
            Toast.makeText(context, "현재 위치 정보 확인을 위해 Wifi와 GPS를 켜주세요.", Toast.LENGTH_SHORT).show()
            return false }
        else if (!locationManager!!.isLocationEnabled ) {
            Toast.makeText(context, "현재 위치 정보 확인을 위해 GPS를 켜주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (connectionManager!!.activeNetwork==null){
            Toast.makeText(context, "현재 위치 정보 확인을 위해 Wifi를 켜주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        else {
            return true
        }
    }


    private fun findUsersLocation(received_naverMap: NaverMap){
        // NAVER MAP 객체 찾아옴.
        if (received_naverMap != null ){ // NaverMap 객체 받아서 NaverMap 객체에 위치 소스 지정
            this.naverMap = received_naverMap
            naverMap!!.locationSource = locationSource // 초기 위치 설정(주의:locationSource!= latitude)
            naverMap!!.locationTrackingMode = LocationTrackingMode.Follow     //권한 허용시(카메라 따라가게 모드 설정 o)
        }

        locationManager = (context)!!.getSystemService()
        connectionManager = (context)!!.getSystemService()

        if (CheckServiceState()) findLocationAddress()  //네트워크가 켜져있지 않은 경우 작동되지 않게 (에러처리)

    }

    private fun findLocationAddress(){
        val geoCoder = Geocoder(context, Locale.getDefault())
        addresslist =  geoCoder.getFromLocation(nowLat, nowLng, 1) //위도,경도 -> 주소로 변환
        address = addresslist!!.get(0).getAddressLine(0)
        // Toast.makeText(context, "위치 :  "+ "${addresslist}", Toast.LENGTH_SHORT).show()

    }





}