package com.example.toyproject_client

import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.data.FavoriteStoreViewmodel
import com.example.toyproject_client.data.StoreInfoViewmodel
import com.example.toyproject_client.data.StoreMenuItem
import com.example.toyproject_client.databinding.FragmentStoreinfoBinding
import com.example.toyproject_client.myserver.PlaceDocument
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.fragment_storeinfo.*
import kotlinx.android.synthetic.main.fragment_storeinfo.recyclerView
import kotlinx.android.synthetic.main.store_menu_item.*
import kotlinx.android.synthetic.main.store_menu_item.view.*

class storeInfoFragment : Fragment(), OnMapReadyCallback {

    //가져온 해당 가게 정보
    private val storeviewModel: FavoriteStoreViewmodel by viewModels()
    private lateinit var binding : FragmentStoreinfoBinding
    var receivedItemdata : PlaceDocument? = null

    //가져온 해당 가게의 메뉴 정보
    private val menuviewmodel : StoreInfoViewmodel by viewModels()

    // 리사이클러뷰 정보
    private lateinit var adapterStore: Menu_RecyclerViewAdapter
    lateinit var viewHolder : Menu_RecyclerViewAdapter.SearchViewHolder


    //Map 띄우기 위한 정보
    private var locationManager : LocationManager?= null //LocationManager
    private var connectionManager : ConnectivityManager?= null //WifiManager
    private var locationSource : FusedLocationSource ? = null
    private var naverMap: NaverMap? = null

    //버튼 바꿀 때 필요한 정보 정보
    var clickstate : Boolean = false  // 좋아하는 가게로 눌려졌는지 여부
    private lateinit var storeID : String
    private var resultSelectedMenuItem : ArrayList<StoreMenuItem> = arrayListOf()
    //private var Menucounts : MutableMap<String, Int> = mutableMapOf()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_storeinfo, container, false)
        val rootView = binding.root
        return  rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receivedItemdata = arguments?.getParcelable("selectedStore")
        clickstate = receivedItemdata!!.clickstate

        //(추가조건) 서버에서 오는 정보의 경우 엔티티에 있는 지 여부 비교를 통해 즐겨찾기 등록 여부 표현 필요.
        storeID = receivedItemdata!!.id
        storeviewModel.checkFavoriteStore(storeID).observe(viewLifecycleOwner){ receivedStoreId ->
            if (receivedStoreId == storeID) { //저장되어 있는 가게의 경우
                clickstate = true
            }
            changeButtonImage(clickstate)
        }

        makeMapView()
        makeView(storeID)

        like_btn.setOnClickListener {
            clickstate = (!clickstate)
            Log.d("BTN", "${clickstate}")
            changeButtonImage(clickstate)
            controlFavoriteStore(clickstate)
        }


        getFoodToMyCartbtn.setOnClickListener {
            val sendingbundlelist: ArrayList<StoreMenuItem> = resultSelectedMenuItem.clone() as ArrayList<StoreMenuItem> //얕은 복사 o (깊은 복사 x)
            sendingbundlelist.removeIf { it.menucount ==0 }
            if (sendingbundlelist.isEmpty()){ Toast.makeText(context, "담긴 음식이 없습니다.", Toast.LENGTH_SHORT).show()  }
            else {
                val bundle = Bundle()
                bundle.putParcelableArrayList("selectedMenuItems", sendingbundlelist)
                findNavController().navigate(R.id.action_storeInfoFragment_to_myCartFragment, bundle)   //넘겨받아서 가게의 메뉴 정보 가져오기.!
            }
        }


        mycart_btn.setOnClickListener {
            findNavController().navigate(R.id.action_storeInfoFragment_to_myCartFragment)
        }
    }


    fun changeButtonImage(clickstate : Boolean){
        if (clickstate == true) like_btn.setImageResource(R.drawable.selectedlikebutton)
        else like_btn.setImageResource(R.drawable.likebutton)
    }

    fun controlFavoriteStore(clickstate: Boolean){
        if (clickstate == true) {
            //Log.d("BTN", "가게를 등록하였습니다..")
            storeviewModel.insertFavoriteStore(receivedItemdata!!)
        }
        else {
            //Log.d("BTN", "가게를 삭제하였습니다..")
            storeviewModel.deleteFavoriteStore(receivedItemdata!!)
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

    private fun makeView(storeID : String){
        binding.storeItem = receivedItemdata

        //해당 가게의 음식들을 가져와야 한다. (메뉴 표현)
        menuviewmodel.getStoreMenuList(storeID).observe(viewLifecycleOwner){ storeMenuItemList ->
            adapterStore = Menu_RecyclerViewAdapter(storeMenuItemList!!)
            recyclerView.adapter = adapterStore

            initRecyclerView_clickListener()    //클릭리스너
            storeMenuItemList.forEach { storeMenuItem ->  resultSelectedMenuItem.add(storeMenuItem)  } //클릭리스너 내부에서 아이템클릭여부(list) 사용위해 초기화 (한번)
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------메뉴클릭 관련 함수들------------------------------

    //private fun calculate


    private fun initRecyclerView_clickListener() {
        adapterStore.apply {
            listener = object : Menu_RecyclerViewAdapter.MenuItemClickListener{
                override fun menuItemCheckClickListener(view : View, position: Int, item: StoreMenuItem) {
                    //showCountLayoutView(viewHolder)
                    showCountLayoutView(position, 0, item)
                }
                override fun menuItemMinusClickListener(view : View, position: Int, item: StoreMenuItem) {
                    showCountLayoutView(position, 1, item)
                }

                override fun menuItemPlusClickListener(view : View, position: Int, item: StoreMenuItem) {
                    showCountLayoutView(position, 2, item)

                }
            }
        }
    }

    private fun showCountLayoutView(position: Int, mode : Int, item: StoreMenuItem){
        viewHolder = recyclerView.findViewHolderForAdapterPosition(position) as Menu_RecyclerViewAdapter.SearchViewHolder

        //var countnum = Menucounts[menuname]!!

        when (mode) {
            0 -> {
                if (viewHolder.itemView.menuItemSelectbtn.isChecked) {  item.menucount = 1
                    viewHolder.itemView.Countingtext.text = "1 개"
                    viewHolder.itemView.Countinglayout.visibility = VISIBLE }
                else {  item.menucount = 0
                    viewHolder.itemView.Countinglayout.visibility = GONE }  }
            1 -> {
                if ( item.menucount > 1)  {   item.menucount -=1
                    viewHolder.itemView.Countingtext.text = item.menucount.toString() + " 개"}
                else Toast.makeText(context, "최소 1개의 수량은 필요합니다.", Toast.LENGTH_SHORT).show()
            }
            2 -> { item.menucount += 1
                viewHolder.itemView.Countingtext.text = item.menucount.toString() + " 개" }
        }
        Log.e("TAG!! before resultSelectedMenuItem", "${resultSelectedMenuItem}")
            resultSelectedMenuItem.set(position, item)
        Log.e("TAG!! after resultSelectedMenuItem", "${resultSelectedMenuItem}")

    }



    /*
    private fun showCountLayoutView(viewHolder : RecyclerView.ViewHolder){      에러나는 방법.
        if (menuItemSelectbtn.isChecked) {   viewHolder.itemView.Countinglayout.visibility = View.VISIBLE }
        else {  viewHolder.itemView.Countinglayout.visibility = View.GONE }
    }*/




}