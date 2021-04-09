package com.example.toyproject_client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MyCartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mycart, container, false)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {    //BackController -> 홈화면 이동
            findNavController().navigate(R.id.myCartFragment_pop)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //receivedItemdata = arguments?.getParcelable("selectedStore")

    }


}