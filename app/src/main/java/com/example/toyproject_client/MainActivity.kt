package com.example.toyproject_client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    /*
    companion object{
        val Appusername : String = "이지윤"   //처음 로그인할 때 여기다가 저장하기. (로그아웃시 제거 필요 !!)
    }
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}