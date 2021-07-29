package com.example.codynatorsfb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeList.setOnClickListener {
            val intent = Intent(this,PlaceListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }
    }

}
