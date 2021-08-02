package com.example.summerproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_check_out.*
import java.util.*

// 2021.08.01 khsexk: 체크아웃 구성
class CheckOutActivity : AppCompatActivity() {
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.getReference("Table Use Information")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        stopUse.setOnClickListener {
            var Table : TableData = TableData("", 0)
            var Map : HashMap<String, Any> = HashMap<String, Any>()
            Map.put("Table 1", Table)

            myRef.updateChildren(Map)
            finish()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }
        cancel.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }
    }
}