package com.example.toyproject_client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.Data.UserData.UserDataViewmodel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.userlocation
import kotlinx.android.synthetic.main.fragment_insertlocationinfo.*

class HomeFragment : Fragment(){
    private val viewModel: UserDataViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        //(가입시) 이미 정보가 있다는 가정하이다.
        viewModel.getUserLocationData().observe(viewLifecycleOwner){
            userlocation.text = it.address
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        location_btn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_insertLocationInfoFragment)
        }
    }


}