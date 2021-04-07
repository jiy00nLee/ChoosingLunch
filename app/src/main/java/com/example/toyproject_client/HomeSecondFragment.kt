package com.example.toyproject_client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.myserver.PlaceDocument
import com.example.toyproject_client.data.UserDataViewmodel
import kotlinx.android.synthetic.main.fragment_fourthhome.*
import kotlinx.android.synthetic.main.fragment_secondhome.recyclerView

class HomeSecondFragment : Fragment() {

    private val viewModel: UserDataViewmodel by viewModels()
    private lateinit var adapterStore: Store_RecyclerViewAdapter

    private var userLat: Double = 0.0
    private var userLng: Double = 0.0
    private var useraddress : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_secondhome, container, false)
        return  rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserLocationData().observe(viewLifecycleOwner) {
            userLat = it.latitude
            userLng = it.longtitude
            useraddress = it.address
            viewModel.getStoreList("한식", userLng, userLat, useraddress).observe(viewLifecycleOwner){resultStorelist ->
                Log.e("들어온 데이터 (한식 ) " , "${resultStorelist}")
                showRecyclerView(resultStorelist)
            }
        }
    }

    private fun showRecyclerView(storelist: List<PlaceDocument>?) {

        if (storelist != null) {
            adapterStore = Store_RecyclerViewAdapter(storelist!!) { placeDocument ->
                val bundle = Bundle()
                bundle?.putParcelable("selectedStore", placeDocument)
                findNavController().navigate(
                    R.id.action_homeFragment_to_storeInfoFragment,
                    bundle
                )
            }//.apply { rc_storeItems = resultplaces!! }
            recyclerView.adapter = adapterStore
        }
    }


}