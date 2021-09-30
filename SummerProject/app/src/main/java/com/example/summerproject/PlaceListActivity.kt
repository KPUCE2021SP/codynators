package com.example.summerproject

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_place_list.*
import org.jetbrains.anko.startActivity

class PlaceListActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)

        place_juciy.setOnClickListener {
            startActivity<JuciyActivity>()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }
    }
}