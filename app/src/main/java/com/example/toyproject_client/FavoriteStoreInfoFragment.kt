package com.example.toyproject_client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.View_Adapter.Store_RecyclerViewAdapter
import com.example.toyproject_client.data.FavoriteStoreViewmodel
import kotlinx.android.synthetic.main.fragment_firsthome.*

class FavoriteStoreInfoFragment : Fragment() {

    private val viewModel: FavoriteStoreViewmodel by viewModels()
    private lateinit var adapterFavoriteStore : Store_RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_favoritestoreinfo, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerView()
    }

    private fun showRecyclerView() {
        adapterFavoriteStore = Store_RecyclerViewAdapter() { placeDocument ->
            val bundle = Bundle()
            bundle.putParcelable("selectedStore", placeDocument)
            findNavController().navigate(
                    R.id.action_homeFragment_to_storeInfoFragment,
                    bundle
            )
        }.apply {
            viewModel.getAllFavoriteStores().observe(viewLifecycleOwner){ observedfavoritestorelist ->
                received_items = observedfavoritestorelist
            }
        }
        recyclerView.adapter =  adapterFavoriteStore
    }


}