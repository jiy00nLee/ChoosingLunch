package com.example.toyproject_client.View_Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.toyproject_client.*

class ViewPagerAdapter(fm: FragmentManager, lc: Lifecycle) : FragmentStateAdapter(fm,lc){

    override fun getItemCount(): Int = 5 //PagerViewadapter에서 관리할 View 개수를 반환한다.

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeFirstFragment()
            1 -> HomeSecondFragment()
            2 -> HomeThirdFragment()
            3 -> HomeFourthFragment()
            4 -> HomeFifthFragment()
            else -> HomeFirstFragment()
        }
    }
    //추가?

}