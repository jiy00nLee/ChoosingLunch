package com.example.toyproject_client.View_Dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.toyproject_client.R
import com.example.toyproject_client.data.StoreInfoViewmodel
import kotlinx.android.synthetic.main.fragment_askchangingdeletedialog.view.*

class askChangingDeleteDialogFragment : DialogFragment() {

    //가져온 해당 가게의 메뉴 정보 & 선택했던 메뉴 정보(장바구니에 담겨있는 정보)
    private val menuviewmodel : StoreInfoViewmodel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_askchangingdeletedialog, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val storeID : String? = arguments?.getString("StoreID")
        view.dialogYesbtn.setOnClickListener {
            menuviewmodel.deleteMenuInfoListByStore(storeID!!)   //이전 저장 정보 지움.
            findNavController().navigate(R.id.action_askChangingDeleteDialogFragment_to_myCartFragment)
        }
        view.dialogNobtn.setOnClickListener {
            findNavController().navigate(R.id.action_askChangingDeleteDialogFragment_to_changingMyCartMenuFragment)
        }
    }

}