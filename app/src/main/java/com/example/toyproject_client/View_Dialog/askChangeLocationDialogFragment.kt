package com.example.toyproject_client.View_Dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.R
import kotlinx.android.synthetic.main.fragment_askchangelocationdialog.view.*

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