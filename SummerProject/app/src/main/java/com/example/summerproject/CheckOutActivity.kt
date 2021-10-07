package com.example.summerproject

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_check_out.*

// 2021.08.01 khsexk: 체크아웃 구성
// 2021.09.15 khsexk: 데이터 양식 변경에 따른 코드 변경
class CheckOutActivity : AppCompatActivity() {
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("Table_Use_Information") // Collection 이름
    var flag: Boolean = false
    var useTable: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        // 2021.10.03 khsexk: 체크아웃 사용 좌석 가져오기
        val uId : String = FirebaseAuth.getInstance().uid.toString()

        itemsCollectionRef
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if(uId==document["userId"]){
                        flag = true
                        useTable = document.id
                        seat.text = useTable
                        break
                    } else {
                        seat.text = "자리를 사용하고 있지 않습니다"
                        stopUse.isEnabled = false
                    }
                } // for
            }
            .addOnFailureListener { exception ->

            }

        stopUse.setOnClickListener {
            val Table = hashMapOf(
                "userId" to "",
                "useInfo" to false
            )

            itemsCollectionRef.document(useTable).set(Table).addOnSuccessListener {// 체크인 ACTIVITY들어왔을 시, useInfo가 true로 변경되는지 체크
                Log.d(ContentValues.TAG, "Update successfully written!")
                Toast.makeText(this, "체크아웃 되었습니다", Toast.LENGTH_LONG).show();
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