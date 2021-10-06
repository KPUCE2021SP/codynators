package com.example.summerproject

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.common.collect.Table
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_check_out.*
import kotlinx.android.synthetic.main.activity_checkin.*
import java.util.*

// 2021.08.01 khsexk: 체크인 구성
class CheckInActivity : AppCompatActivity() {
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("Table_Use_Information") // Collection 이름
    val uId : String = FirebaseAuth.getInstance().uid.toString()

    // 2021.10.05 khsexk: 이미 좌석을 사용중일 때 예외 처리
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkin)


        itemsCollectionRef
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if(document["useInfo"] == true || document["userId"] == uId){
                        flag.text = "true"
                        break
                    } else{
                        flag.text = "false"
                    }
                } // for
            }
            .addOnFailureListener { exception ->

            }

        IntentIntegrator(this).initiateScan()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Unit {
        val result : IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {

                // Firebase Realtime DB에 쓰기
//              var useTable : String = result.getContents()
//              var Table : TableData = TableData("", 1)
//              var Map : HashMap<String, Any> = HashMap<String, Any>()
//              Map.put(useTable, Table)
//
//              myRef.updateChildren(Map)

                //Firebase Cloude Store에 useInfo 값 update : written by 태용
                // 2021.09.13 : update by 현석
                if(flag.text == "true"){
                    Toast.makeText(this, "이미 자리를 사용중입니다.", Toast.LENGTH_LONG).show();
                    finish()
                } else{
                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    var useTable : String = result.getContents()

                    val Table: TableData = TableData(uId, true)

                    itemsCollectionRef.document(useTable).set(Table).addOnSuccessListener {// 체크인 ACTIVITY들어왔을 시, useInfo가 true로 변경되는지 체크
                        Log.d(ContentValues.TAG, "Update successfully written!")
                    }

                    finish()
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}