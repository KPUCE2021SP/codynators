package com.example.qr_firebase_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.HashMap

/*
class MainActivity : AppCompatActivity() {
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.getReference("message")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRef.setValue("Hi Firebase Realtime DB")
    }
}*/
class MainActivity : AppCompatActivity() {
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.getReference("Table Use Information")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 데이터 생성
/*        var Table : Table_Data = Table_Data("", 0)
        var Map : HashMap<String, Any> = HashMap<String, Any>()
        Map.put("Table 1", Table)
        Map.put("Table 2", Table)
        Map.put("Table 3", Table)

        myRef.updateChildren(Map)
*/

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