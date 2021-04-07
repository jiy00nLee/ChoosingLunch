package com.example.toyproject_client

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.toyproject_client.data.StoreMenuItem
import com.example.toyproject_client.databinding.StoreItemBinding
import com.example.toyproject_client.databinding.StoreMenuItemBinding
import kotlinx.android.synthetic.main.store_menu_item.view.*


class Menu_RecyclerViewAdapter(var received_menuitems : List<StoreMenuItem>) : RecyclerView.Adapter<Menu_RecyclerViewAdapter.SearchViewHolder>() {


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
        LayoutInflater.from(parent.context).inflate(R.layout.store_menu_item, parent, false)) //(커스텀) 뷰 홀더 -> '하나짜리 담당'! 생성할 '뷰'만 구성. (no clickinterface)
    {
        fun bind (data: StoreMenuItem){
            // binding.storeMenuItems = data
            itemView.menuName.text = data.menuname
            itemView.menuPrice.text = data.menuprice.toString()

        }
    }


}