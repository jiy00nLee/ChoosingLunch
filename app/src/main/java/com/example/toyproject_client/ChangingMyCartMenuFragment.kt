package com.example.toyproject_client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.data.FavoriteStoreViewmodel
import com.example.toyproject_client.data.StoreInfoViewmodel
import com.example.toyproject_client.data.StoreMenuItem
import kotlinx.android.synthetic.main.changing_store_menu_item.view.*
import kotlinx.android.synthetic.main.fragment_changingmycartmenu.*
import kotlinx.android.synthetic.main.fragment_changingmycartmenu.recyclerView


class ChangingMyCartMenuFragment : DialogFragment() {

    //가져온 해당 가게 정보 & 선택한 메뉴 정보(장바구니에 담을 정보)
    private val menuviewmodel : StoreInfoViewmodel by viewModels()
    private var storeID: String = ""
    private var storeName: String = ""

    // 리사이클러뷰 정보
    private lateinit var adapterStore: ChangingMenu_RecyclerViewAdapter
    lateinit var viewHolder : ChangingMenu_RecyclerViewAdapter.SearchViewHolder

    //클릭리스너 정보
    private var resultStoreMenuItem : ArrayList<StoreMenuItem> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_changingmycartmenu, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storeID = arguments?.getString("storeID")!!
        storeName = arguments?.getString("storeName")!!

        menuviewmodel.getStoreMenuList(storeID).observe(viewLifecycleOwner){
            resultStoreMenuItem.clear()
            resultStoreMenuItem.addAll(it)

            makeView()
        }

        dialogChangebtn.setOnClickListener {
            Log.e("checking!!", "${resultStoreMenuItem}")
            val sendingbundlelist: ArrayList<StoreMenuItem> = resultStoreMenuItem.clone() as ArrayList<StoreMenuItem> //얕은 복사 o (깊은 복사 x)
            Log.e("checking!!!", "${sendingbundlelist}")
            sendingbundlelist.removeIf { !it.ischecked || it.menucount == 0}
            Log.e("checking!!!!", "${sendingbundlelist}")
            if (sendingbundlelist.isEmpty()){ Toast.makeText(context, "담긴 음식이 없습니다.", Toast.LENGTH_SHORT).show()  }     //이거에서 담으신 걸 다 없애시겠스비까!!!!!!!
            else {
                menuviewmodel.deleteMenuInfoListByStore(storeID)   //이전 저장 정보 지움.
                sendingbundlelist.forEach {
                    menuviewmodel.insertMenuInfoData(it)        // 새 정보 저장.
                }
                findNavController().navigate(R.id.action_changingMyCartMenuFragment_to_myCartFragment)   //넘겨받아서 가게의 메뉴 정보 가져오기.!
            }
        }

    }


    private fun makeView(){

        storePlacename.text =  storeName

        adapterStore = ChangingMenu_RecyclerViewAdapter().apply {
            menuviewmodel.getMenuInfoListByStore(storeID).observe(viewLifecycleOwner) { storeMenuItemList ->

                storeMenuItemList.forEach { selecteditem ->
                    resultStoreMenuItem.forEach {
                        if (it.menuname== selecteditem.menuname) {
                            it.ischecked = true
                            it.menucount = selecteditem.menucount }
                            //이거 체킹하기.
                    }
                }
                Log.e("checking!", "${resultStoreMenuItem}")
                received_menuitems = resultStoreMenuItem
            }
        }
        recyclerView.adapter = adapterStore
        initRecyclerView_clickListener()    //클릭리스너
    }


    private fun initRecyclerView_clickListener() {
        adapterStore.apply {
            listener = object : ChangingMenu_RecyclerViewAdapter.MenuItemClickListener{
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
        viewHolder = recyclerView.findViewHolderForAdapterPosition(position) as ChangingMenu_RecyclerViewAdapter.SearchViewHolder

        //var countnum = Menucounts[menuname]!!

        when (mode) {
            0 -> {
                if (viewHolder.itemView.menuItemSelectbtn.isChecked) {
                    // if (item.menucount == 0)  { item.menucount = 1 }
                    viewHolder.itemView.Countingtext.text = item.menucount.toString() + " 개"
                    viewHolder.itemView.Countinglayout.visibility = View.VISIBLE
                    item.ischecked = true }
                else { viewHolder.itemView.Countinglayout.visibility = View.GONE
                    item.ischecked = false }
            }
            1 -> {
                if ( item.menucount > 1)  {   item.menucount -=1
                    viewHolder.itemView.Countingtext.text = item.menucount.toString() + " 개"}
                else Toast.makeText(context, "최소 1개의 수량은 필요합니다.", Toast.LENGTH_SHORT).show()
            }
            2 -> { item.menucount += 1
                viewHolder.itemView.Countingtext.text = item.menucount.toString() + " 개" }
        }
        Log.e("checking2222", "${resultStoreMenuItem}")
        //resultSelectedMenuItem.set(position, item)

    }



}
