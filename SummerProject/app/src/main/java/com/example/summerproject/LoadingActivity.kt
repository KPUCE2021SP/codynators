package com.example.summerproject

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        startLoading()
    }
    private fun startLoading(){
        val handler = Handler()
        handler.postDelayed({finish()},2000)
//        overridePendingTransition(R.anim.fadein,R.anim.fadeout)
    }
}