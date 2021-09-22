package com.example.summerproject

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.common.collect.Table
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_check_out.*
import java.util.*

// 2021.08.01 khsexk: 체크아웃 구성
// 2021.09.15 khsexk: 데이터 양식 변경에 따른 코드 변경
class CheckOutActivity : AppCompatActivity() {
    val db: FirebaseFirestore = Firebase.firestore
    val itemsCollectionRef = db.collection("Table_Use_Information") // Collection 이름

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        stopUse.setOnClickListener {
            val Table = hashMapOf(
                "userId" to "",
                "useInfo" to false
            )

            itemsCollectionRef.document("Table_1").set(Table).addOnSuccessListener {// 체크인 ACTIVITY들어왔을 시, useInfo가 true로 변경되는지 체크
                Log.d(ContentValues.TAG, "Update successfully written!")
            }
            finish()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }
        cancel.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }
    }
}