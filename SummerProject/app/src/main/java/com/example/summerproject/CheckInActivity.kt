package com.example.summerproject

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import java.util.*

// 2021.08.01 khsexk: 체크인 구성
class CheckInActivity : AppCompatActivity() {
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.getReference("Table Use Information")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkin)

        IntentIntegrator(this).initiateScan()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Unit {
        val result : IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        val db: FirebaseFirestore = Firebase.firestore
        val itemsCollectionRef = db.collection("Table use Information") // Collection 이름

        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();


                // Firebase Realtime DB에 쓰기
//                var useTable : String = result.getContents()
//                var Table : TableData = TableData("", 1)
//                var Map : HashMap<String, Any> = HashMap<String, Any>()
//                Map.put(useTable, Table)
//
//                myRef.updateChildren(Map)

                //Firebase Cloude Store에 useInfo 값 update : written by 태용
                itemsCollectionRef.document("Table 1").update("useInfo", true).addOnSuccessListener {// 체크인 ACTIVITY들어왔을 시, useInfo가 true로 변경되는지 체크
                    Log.d(ContentValues.TAG, "Update successfully written!")
                }
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}