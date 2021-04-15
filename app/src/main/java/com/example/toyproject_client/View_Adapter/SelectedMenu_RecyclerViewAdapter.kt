package com.example.toyproject_client.View_Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toyproject_client.R
import com.example.toyproject_client.data.MenuInfoData.CartMenuItem
import kotlinx.android.synthetic.main.mycart_item.view.*
import kotlinx.android.synthetic.main.mycart_item.view.menuItemSelectbtn


class SelectedMenu_RecyclerViewAdapter() : RecyclerView.Adapter<SelectedMenu_RecyclerViewAdapter.SearchViewHolder>() {
    //클릭리스너 인터페이스 넘겨주기
    var listener : MenuItemClickListener?= null

    var received_menuitems : List<CartMenuItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface MenuItemClickListener {
        fun menuItemCheckClickListener(position: Int, item: CartMenuItem)
        fun changeSelectedMenuClickListener(position: Int, item: CartMenuItem)

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
        LayoutInflater.from(parent.context).inflate(R.layout.mycart_item, parent, false)) //(커스텀) 뷰 홀더 -> '하나짜리 담당'! 생성할 '뷰'만 구성. (no clickinterface)
    {
        fun bind (data: CartMenuItem){
            val totalPrice = data.totalmenuprice.toString() + "원"
            itemView.StoreName.text = data.storename
            itemView.selectedMenues.text = data.menuinfotext
            itemView.totalMenuPrice.text = totalPrice
        }
        init {
            //체크버튼이 눌러졌을 때.
            itemView.menuItemSelectbtn.setOnClickListener{
                listener?.menuItemCheckClickListener(adapterPosition, received_menuitems[adapterPosition])
            }
            itemView.changeSelectedMenubtn.setOnClickListener{
                listener?.changeSelectedMenuClickListener(adapterPosition, received_menuitems[adapterPosition])
            }
        } //클릭리스너


    }






}