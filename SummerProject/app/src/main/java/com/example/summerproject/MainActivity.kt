package com.example.summerproject

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : Activity() {
    // 2021.08.01 khsexk: rdb 연동
//    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
//    val myRef : DatabaseReference = database.getReference("Table Use Information") made by 현석
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 2021.08.01 khsexk: rdb 초기 데이터 생성 -> 생성후 삭제코드
//        var Table : TableData = TableData("", 0)
//        var Map : HashMap<String, Any> = HashMap<String, Any>()
//        Map.put("Table 1", Table)
//        Map.put("Table 2", Table)
//        Map.put("Table 3", Table)
//        myRef.updateChildren(Map) made by 현석

        /*Cloud Firestore 로 변경하기 위해 작업*/
        val db: FirebaseFirestore = Firebase.firestore // : written by 태용
        val array : ArrayList<String> = arrayListOf("Table 1", "Table 2", "Table 3") // 테이블 3개로 테스트 : written by 태용

        val itemsCollectionRef = db.collection("Table use Information") // Collection 이름 : written by 태용
//
        val itemMap = hashMapOf( // to 앞의 값은 Key, to 뒤의 값은 value : written by 태용
                "userID" to "${FirebaseAuth.getInstance().currentUser}",
                "useInfo" to false,
                "useTable" to " "
        )

        //board.bringToFront()

        for(it in array){ // "Table use Information"이라는 컬렉션 밑에  table1, table2, table3 document 추가하고 각 document에 key : value 삽입
            // : written by 태용
            itemsCollectionRef.document(it)
                    .set(itemMap)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!")  }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }

        // 2021.08.01 khsexk: 체크인 and 체크아웃
        checkIn.setOnClickListener{
            startActivity<CheckInActivity>()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }
        checkOut.setOnClickListener {
            startActivity<CheckOutActivity>()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }

        placeList.setOnClickListener {
            startActivity<PlaceListActivity>()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }
    }

}