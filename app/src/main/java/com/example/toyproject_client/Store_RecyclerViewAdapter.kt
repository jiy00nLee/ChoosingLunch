package com.example.toyproject_client

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toyproject_client.databinding.StoreItemBinding
import com.example.toyproject_client.myserver.PlaceDocument
import kotlinx.android.synthetic.main.store_item.view.*
import kotlinx.android.synthetic.main.store_menu_item.view.*

class Store_RecyclerViewAdapter(val itemClick : (PlaceDocument) ->Unit) : RecyclerView.Adapter<Store_RecyclerViewAdapter.SearchViewHolder>() {

    //private lateinit var binding : StoreItemBinding
    var received_items : List<PlaceDocument> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return received_items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        //binding = StoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(received_items[position])    //리스트에서 하나를 뽑아서 (커스텀)뷰홀더에 전달해줌.
    }


    inner class SearchViewHolder (parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.store_item, parent, false))  //(커스텀) 뷰 홀더 -> '하나짜리 담당'! 생성할 '뷰'만 구성. (no clickinterface)
    {
        fun bind (data : PlaceDocument){
            //binding.storeItem = data //하나를 binding.
            itemView.storePlacename.text = data.place_name
            itemView.storeCategoryname.text = data.category_name
            itemView.storePlacelocation.text = data.road_address_name

            itemView.setOnClickListener {  itemClick(data)  }
        }
    }


}

