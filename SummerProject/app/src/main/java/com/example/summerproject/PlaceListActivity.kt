package com.example.summerproject

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_place_list.*

class PlaceListActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)

        placeListToMain.bringToFront()

        placeListToMain.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }
    }
}