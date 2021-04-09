package com.example.toyproject_client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.data.MenuInfoData.CartMenuItem
import com.example.toyproject_client.data.StoreInfoViewmodel
import com.example.toyproject_client.data.StoreMenuItem
import kotlinx.android.synthetic.main.fragment_mycart.*
import kotlinx.android.synthetic.main.fragment_storeinfo.*
import kotlinx.android.synthetic.main.fragment_storeinfo.recyclerView
import kotlinx.android.synthetic.main.mycart_item.view.*


class MyCartFragment : Fragment() {

    //가져온 해당 가게의 메뉴 정보 & 선택한 메뉴 정보(장바구니에 담을 정보)
    private val menuviewmodel : StoreInfoViewmodel by viewModels()
    private var CartMenuItemList : MutableList<CartMenuItem> = mutableListOf()

    // 리사이클러뷰 정보
    private lateinit var adapterMycart: SelectedMenu_RecyclerViewAdapter
    lateinit var viewHolder : SelectedMenu_RecyclerViewAdapter.SearchViewHolder


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mycart, container, false)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {    //BackController -> 홈화면 이동
            findNavController().navigate(R.id.myCartFragment_pop)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeMycartView()

        //buybtn


    }

    private fun makeMycartView(){
        menuviewmodel.getAllStoreIDsFromMycart().observe(viewLifecycleOwner){ storeIDlist ->
            storeIDlist.forEach { storeID ->
                menuviewmodel.getMenuInfoListByStore(storeID).observe(viewLifecycleOwner){ storemenulist -> //한 가게의 메뉴 리스트들을 받아옴.
                    val storename : String = storemenulist[0].storename
                    var menuinfotext : String = ""
                    var totalmenuprice : Int = 0
                    var storeid : String = ""
                    storemenulist.forEach { storemenu ->
                        menuinfotext = menuinfotext + storemenu.menuname + "(" + storemenu.menucount.toString() + ")\n"
                        totalmenuprice = totalmenuprice + storemenu.menuprice*storemenu.menucount
                        storeid = storemenu.storeid
                    }
                    CartMenuItemList.add(CartMenuItem(storename, menuinfotext, totalmenuprice, storeid ))

                    adapterMycart = SelectedMenu_RecyclerViewAdapter(CartMenuItemList)
                    recyclerView.adapter = adapterMycart
                    initRecyclerView_clickListener()
                }
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

        //var countnum = Menucounts[menuname]!!

        when (mode) {
            0 -> {  //if (viewHolder.itemView.menuItemSelectbtn.isChecked)
                 }
            1 -> {
                val bundle = Bundle()
                bundle.putString("storeID", item.storeid)
                findNavController().navigate(R.id.action_myCartFragment_to_changingMyCartMenuFragment, bundle) //해당 프레그먼트에 번들로 Store의 프라이머리 키값인 storeid를 넘겨줌.
            }
        }
    }



}