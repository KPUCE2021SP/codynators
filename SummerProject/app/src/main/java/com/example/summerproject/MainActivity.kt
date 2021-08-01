package com.example.summerproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    // 2021.08.01 khsexk: rdb 연동
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.getReference("Table Use Information")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 2021.08.01 khsexk: rdb 초기 데이터 생성 -> 생성후 삭제코드
        var Table : TableData = TableData("", 0)
        var Map : HashMap<String, Any> = HashMap<String, Any>()
        Map.put("Table 1", Table)
        Map.put("Table 2", Table)
        Map.put("Table 3", Table)
        myRef.updateChildren(Map)

        // 2021.08.01 khsexk: 체크인 and 체크아웃
        checkIn.setOnClickListener{
            val intent = Intent(this,CheckInActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }
        checkOut.setOnClickListener {
            val intent = Intent(this,CheckOutActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }

        placeList.setOnClickListener {
            val intent = Intent(this,PlaceListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }
    }

}