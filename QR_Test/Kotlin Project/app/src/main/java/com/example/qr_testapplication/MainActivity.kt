package com.example.qr_testapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createBtn.setOnClickListener {
            val intent = Intent(this, QR_CreateActivity::class.java)
            startActivity(intent)
        }
        scanBtn.setOnClickListener {
            val intent = Intent(this, QR_ScanActivity::class.java)
            startActivity(intent)
        }

    }
}