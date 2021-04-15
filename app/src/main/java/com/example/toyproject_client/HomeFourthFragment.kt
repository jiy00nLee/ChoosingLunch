package com.example.toyproject_client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.View_Adapter.Store_RecyclerViewAdapter
import com.example.toyproject_client.data.UserDataViewmodel
import kotlinx.android.synthetic.main.fragment_fourthhome.recyclerView

class HomeFourthFragment : Fragment() {

    private val viewModel: UserDataViewmodel by viewModels()
    private lateinit var adapterStore: Store_RecyclerViewAdapter

    private var userLat: Double = 0.0
    private var userLng: Double = 0.0
    private var useraddress : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_fourthhome, container, false)
        return  rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecyclerView()
    }

    private fun showRecyclerView() {
        adapterStore = Store_RecyclerViewAdapter() { placeDocument ->
            val bundle = Bundle()
            bundle.putParcelable("selectedStore", placeDocument)
            findNavController().navigate(
                    R.id.action_homeFragment_to_storeInfoFragment,
                    bundle
            )
        }.apply {
            viewModel.getUserLocationData().observe(viewLifecycleOwner) {
                userLat = it.latitude
                userLng = it.longtitude
                useraddress = it.address
                viewModel.getStoreList("일식", userLng, userLat, useraddress).observe(viewLifecycleOwner){observedresultStorelist ->
                    received_items = observedresultStorelist
                }
            }
        }
        recyclerView.adapter = adapterStore
    }


}