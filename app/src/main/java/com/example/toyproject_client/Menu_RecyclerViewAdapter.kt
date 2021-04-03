package com.example.toyproject_client

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toyproject_client.data.storeMenu.StoreMenuItem
import com.example.toyproject_client.databinding.FragmentStoreinfoBinding
import com.example.toyproject_client.databinding.StoreItemBinding
import com.example.toyproject_client.myserver.PlaceDocument

class Menu_RecyclerViewAdapter(received_items : List<StoreMenuItem>) : RecyclerView.Adapter<Menu_RecyclerViewAdapter.SearchViewHolder>() {

    private lateinit var binding : StoreItemBinding //Binding 바꿔줘야함. !!!!!
    var rc_storeItems = received_items


    override fun getItemCount(): Int {
        return rc_storeItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = StoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(rc_storeItems[position])    //리스트에서 하나를 뽑아서 (커스텀)뷰홀더에 전달해줌.
    }


    inner class SearchViewHolder (binding: StoreItemBinding) : RecyclerView.ViewHolder(binding.root) //(커스텀) 뷰 홀더 -> '하나짜리 담당'! 생성할 '뷰'만 구성. (no clickinterface)
    {
        fun bind (data: StoreMenuItem){
            //binding.storeItem = data //하나를 binding.

        }
    }


}