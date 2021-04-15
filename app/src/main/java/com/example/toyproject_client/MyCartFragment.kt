package com.example.toyproject_client

import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.util.containsValue
import androidx.core.util.set
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.View_Adapter.SelectedMenu_RecyclerViewAdapter
import com.example.toyproject_client.data.MenuInfoData.CartMenuItem
import com.example.toyproject_client.data.StoreInfoViewmodel
import kotlinx.android.synthetic.main.fragment_mycart.*
import kotlinx.android.synthetic.main.fragment_mycart.recyclerView
import kotlinx.android.synthetic.main.mycart_item.view.*
import java.text.DecimalFormat


class MyCartFragment : Fragment() {

    //가져온 해당 가게의 메뉴 정보 & 선택한 메뉴 정보(장바구니에 담을 정보)
    private val menuviewmodel : StoreInfoViewmodel by viewModels()
    private val CartMenuItemList : ArrayList<CartMenuItem> = arrayListOf()

    // 리사이클러뷰 정보
    private lateinit var adapterMycart: SelectedMenu_RecyclerViewAdapter
    lateinit var viewHolder : SelectedMenu_RecyclerViewAdapter.SearchViewHolder

    //리사이클러뷰 내부 아이템 정보
    private val decform : DecimalFormat = DecimalFormat("#,###")
    private var storename : String = ""
    private var menuinfotext : String = ""
    private var menupriceinfotext : String = ""
    private var totalmenuprice : Int = 0
    private var storeid : String = ""

    //버튼 바꿀 때 필요한 정보
    private val clickeditemsList : SparseBooleanArray = SparseBooleanArray()    //한아이템마다의 클릭x, 전체중 하나라도 클릭여부 있는지 판단 -> Sparse!
    private var cloneCheckinglist: ArrayList<CartMenuItem> = arrayListOf()
    private val bundle : Bundle = Bundle()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mycart, container, false)

          val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {    //BackController -> 홈화면 이동
            findNavController().navigate(R.id.action_myCartFragment_to_homeFragment)  }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeMycartView()

        //삭제 버튼 구현
        deleteCartItem_btn.setOnClickListener {
            cloneCheckinglist.clear()
            cloneCheckinglist = CartMenuItemList.clone() as ArrayList<CartMenuItem> //얕은 복사 o (깊은 복사 x)
            cloneCheckinglist.removeIf { !it.clickedstate }
            if (cloneCheckinglist.isEmpty()){ Toast.makeText(context, "삭제할 아이템이 없습니다.", Toast.LENGTH_SHORT).show()  }
            else cloneCheckinglist.forEach {  menuviewmodel.deleteMenuInfoListByStore(it.storeid)  }
        }

        buybtn.setOnClickListener {
            cloneCheckinglist.clear()
            cloneCheckinglist = CartMenuItemList.clone() as ArrayList<CartMenuItem> //얕은 복사 o (깊은 복사 x)
            cloneCheckinglist.removeIf { !it.clickedstate }

            bundle.clear()
            bundle.putParcelableArrayList("selectedList", cloneCheckinglist)
            findNavController().navigate(R.id.action_myCartFragment_to_buyFragment, bundle)
        }
    }

    private fun makeMycartView(){

        menuviewmodel.getAllStoreIDsFromMycart().observe(viewLifecycleOwner){ storeIDlist ->

            buybtn.visibility = View.INVISIBLE
            if (storeIDlist.isEmpty())  {
                recyclerView.visibility = View.INVISIBLE
            }

            storeIDlist.forEach { storeID ->
                CartMenuItemList.clear()
                clickeditemsList.clear()

                adapterMycart = SelectedMenu_RecyclerViewAdapter().apply {

                    menuviewmodel.getMenuInfoListByStore(storeID).observe(viewLifecycleOwner){ storemenulist -> //한 가게의 메뉴 리스트들을 받아옴.
                        //이미 이전의 장바구니에 담은게 있을 경우 -> 삭제할건지 물어봐야함!!!!
                        if (storemenulist.isNotEmpty()){
                            storename = storemenulist[0].storename
                            menuinfotext = ""
                            menupriceinfotext = ""
                            totalmenuprice = 0
                            storeid  = ""

                            storemenulist.forEach { storemenu ->
                                menuinfotext = menuinfotext + storemenu.menuname + "(" + storemenu.menucount.toString() + ")\n"
                                menupriceinfotext = menupriceinfotext + decform.format((storemenu.menuprice)*(storemenu.menucount)).toString() + "원\n"
                                totalmenuprice = totalmenuprice + storemenu.menuprice*storemenu.menucount
                                storeid = storemenu.storeid
                            }
                            if (!CartMenuItemList.contains(CartMenuItem(storename, menuinfotext, totalmenuprice, storeid, menupriceinfotext))){
                                CartMenuItemList.add(CartMenuItem(storename, menuinfotext, totalmenuprice, storeid, menupriceinfotext))
                            }
                            //CartMenuItemList.add(CartMenuItem(storename, menuinfotext, totalmenuprice, storeid ))
                        }
                        received_menuitems  = CartMenuItemList
                    }
                }
                recyclerView.adapter = adapterMycart
                initRecyclerView_clickListener()
            }

            storeCountnum.text = "(" + storeIDlist.size.toString() + ")"
        }

    }

    private fun initRecyclerView_clickListener() {
        adapterMycart.apply {
            listener = object : SelectedMenu_RecyclerViewAdapter.MenuItemClickListener{
                override fun menuItemCheckClickListener(position: Int, item: CartMenuItem) {
                    //showCountLayoutView(viewHolder)
                    showCountLayoutView(position, 0, item)
                }
                override fun changeSelectedMenuClickListener(position: Int, item: CartMenuItem) {
                    showCountLayoutView(position, 1, item)
                }
            }
        }
    }

    private fun showCountLayoutView(position: Int, mode : Int, item: CartMenuItem){
        viewHolder = recyclerView.findViewHolderForAdapterPosition(position) as SelectedMenu_RecyclerViewAdapter.SearchViewHolder

        when (mode) {
            0 -> {
                //[로직 1] 전체 중 클릭된 아이가 있는 가 없는 가
                clickeditemsList[position] = viewHolder.itemView.menuItemSelectbtn.isChecked  //개개인 버튼의 클릭여부를 list안에 넣어줌.
                if(clickeditemsList.containsValue(true)) buybtn.visibility = View.VISIBLE //결제 상황 구현해주어야 함!!!!!!!!!!(위에서 oncliscklistern)
                else buybtn.visibility = View.INVISIBLE

                //[로직 2] 클릭 된 아이 자체의 데이터 변경
                item.clickedstate = viewHolder.itemView.menuItemSelectbtn.isChecked
            }
            1 -> {  bundle.clear()
                    bundle.putString("storeID", item.storeid)
                    bundle.putString("storeName", item.storename)
                    findNavController().navigate(R.id.action_myCartFragment_to_changingMyCartMenuFragment, bundle) //해당 프레그먼트에 번들로 Store의 프라이머리 키값인 storeid를 넘겨줌.
            }
        }
    }



}