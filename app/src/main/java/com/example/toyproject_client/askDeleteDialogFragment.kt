package com.example.toyproject_client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.data.StoreInfoViewmodel
import kotlinx.android.synthetic.main.fragment_askdeletedialog.view.*

class askDeleteDialogFragment : DialogFragment() {

    //가져온 해당 가게의 메뉴 정보 & 선택했던 메뉴 정보(장바구니에 담겨있는 정보)
    private val menuviewmodel : StoreInfoViewmodel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_askdeletedialog, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val storeID : String? = arguments?.getString("StoreID")
        view.dialogYesbtn.setOnClickListener {
            menuviewmodel.deleteMenuInfoListByStore(storeID!!)
            findNavController().navigate(R.id.action_askDeleteDialogFragment_to_storeInfoFragment)
        }
        view.dialogNobtn.setOnClickListener {
            Toast.makeText(context, "장바구니로 이동합니다.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_askDeleteDialogFragment_to_myCartFragment)
        }



    }

}