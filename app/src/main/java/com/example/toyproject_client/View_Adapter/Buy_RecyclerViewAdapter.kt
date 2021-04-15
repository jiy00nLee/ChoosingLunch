package com.example.toyproject_client.View_Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toyproject_client.R
import com.example.toyproject_client.data.MenuInfoData.CartMenuItem
import kotlinx.android.synthetic.main.buy_item.view.*
import java.text.DecimalFormat


class Buy_RecyclerViewAdapter() : RecyclerView.Adapter<Buy_RecyclerViewAdapter.SearchViewHolder>() {
    private val decform : DecimalFormat = DecimalFormat("#,###")
    private var totalstorePricetext : String = ""

    var received_items : List<CartMenuItem> = listOf()
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
            LayoutInflater.from(parent.context).inflate(R.layout.buy_item, parent, false))  //(커스텀) 뷰 홀더 -> '하나짜리 담당'! 생성할 '뷰'만 구성. (no clickinterface)
    {
        fun bind (data : CartMenuItem){
            //binding.storeItem = data //하나를 binding.
            itemView.StoreName.text = data.storename
            totalstorePricetext = decform.format(data.totalmenuprice).toString() + "원"
            itemView.StoretotalPrice.text = totalstorePricetext
            itemView.selectedStoremenuName.text = data.menuinfotext
            itemView.selectedStoremenuPrice.text = data.menupriceinfotext

        }
    }


}