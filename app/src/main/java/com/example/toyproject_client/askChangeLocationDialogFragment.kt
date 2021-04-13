package com.example.toyproject_client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_askchangelocationdialog.view.*
import kotlinx.android.synthetic.main.fragment_insertlocationinfo.*

class askChangeLocationDialogFragment : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_askchangelocationdialog, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.dialogYesbtn.setOnClickListener {
            findNavController().navigate(R.id.action_askChangeLocationDialogFragment_to_insertLocationInfoFragment)
        }
        view.dialogNobtn.setOnClickListener {
            findNavController().navigate(R.id.action_askChangeLocationDialogFragment_to_homeFragment)
        }



    }

}