package com.example.toyproject_client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.data.FavoriteStoreViewmodel
import com.example.toyproject_client.data.UserDataViewmodel
import com.example.toyproject_client.myserver.PlaceDocument
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

        viewModel.getAllFavoriteStores().observe(viewLifecycleOwner){ favoritestorelist ->
            showRecyclerView(favoritestorelist)
        }
    }

    private fun showRecyclerView(favoritestorelist: List<PlaceDocument>?) {

        adapterFavoriteStore = Store_RecyclerViewAdapter(favoritestorelist!!) { placeDocument ->
            val bundle = Bundle()
            bundle?.putParcelable("selectedStore", placeDocument)
            findNavController().navigate(R.id.action_favoriteStoreInfoFragment_to_storeInfoFragment, bundle )
        }//.apply { rc_storeItems = resultplaces!! }
        recyclerView.adapter = adapterFavoriteStore
    }


}