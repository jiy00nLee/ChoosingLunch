package com.example.toyproject_client.View_Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toyproject_client.R
import com.example.toyproject_client.data.StoreMenuItem
import kotlinx.android.synthetic.main.changing_store_menu_item.view.*


class ChangingMenu_RecyclerViewAdapter() : RecyclerView.Adapter<ChangingMenu_RecyclerViewAdapter.SearchViewHolder>() {
    //클릭리스너 인터페이스 넘겨주기
    var listener : MenuItemClickListener?= null

    var received_menuitems : List<StoreMenuItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface MenuItemClickListener {
        fun menuItemCheckClickListener(view : View, position: Int, item: StoreMenuItem) //얘가 아이템 데이터가 필요한가?!!!!!!!!!!!!!!
        fun menuItemMinusClickListener(view : View, position: Int, item: StoreMenuItem)
        fun menuItemPlusClickListener(view : View, position: Int, item: StoreMenuItem)
    }


    override fun getItemCount(): Int {
        return received_menuitems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        //binding = StoreMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(parent) //binding대신
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(received_menuitems[position])    //리스트에서 하나를 뽑아서 (커스텀)뷰홀더에 전달해줌.
    }

    //binding : StoreMenuItemBinding , binding.root 대신
    inner class SearchViewHolder (parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.changing_store_menu_item, parent, false)) //(커스텀) 뷰 홀더 -> '하나짜리 담당'! 생성할 '뷰'만 구성. (no clickinterface)
    {
        fun bind (data: StoreMenuItem){
            // binding.storeMenuItems = data
            val price = data.menuprice.toString() + "원"
            itemView.menuName.text = data.menuname
            itemView.menuPrice.text = price

            //뷰 생성시에만 해당됨. (따라서 클릭여부에 따라 변하는 건 리스너로 바꾸어 주어야 함.)
            itemView.menuItemSelectbtn.isChecked = data.ischecked
            if (data.ischecked) {
                itemView.Countinglayout.visibility = View.VISIBLE
                itemView.Countingtext.text = data.menucount.toString() + " 개"
            }
            else itemView.Countinglayout.visibility = View.GONE
        }


        init {
            //체크버튼이 눌러졌을 때.
            itemView.menuItemSelectbtn.setOnClickListener{
                listener?.menuItemCheckClickListener(it, adapterPosition, received_menuitems[adapterPosition])
            }

            itemView.minusbtn.setOnClickListener {
                listener?.menuItemMinusClickListener(it, adapterPosition, received_menuitems[adapterPosition])
            }

            itemView.plusbtn.setOnClickListener {
                listener?.menuItemPlusClickListener(it, adapterPosition, received_menuitems[adapterPosition])
            }

        }//클릭리스너


    }




}