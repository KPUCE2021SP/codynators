package com.example.summerproject

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_checkin.*
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.activity_juciy.*

class JuciyActivity : AppCompatActivity() {

    private val db: FirebaseFirestore = Firebase.firestore
    private val tablesRef = db.collection("Table_Use_Information")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juciy)
        val tables : ArrayList<CardView> = arrayListOf(Table_1,Table_2,Table_3,Table_4,Table_5,Table_6,Table_7,Table_8,Table_9,Table_10,Table_11,Table_12,Table_13,Table_14) //xml상의 테이블 객체들

        var tableClassInput1 = Table("Table_1",Table_1)
        var tableClassInput2 = Table("Table_2", Table_2)
        var tableClassInput3 = Table("Table_3", Table_3)
        var tableClassInput4 = Table("Table_4", Table_4)
        var tableClassInput5 = Table("Table_5", Table_5)
        var tableClassInput6 = Table("Table_6", Table_6)
        var tableClassInput7 = Table("Table_7", Table_7)
        var tableClassInput8 = Table("Table_8", Table_8)
        var tableClassInput9 = Table("Table_9", Table_9)
        var tableClassInput10 = Table("Table_10", Table_10)
        var tableClassInput11 = Table("Table_11", Table_11)
        var tableClassInput12 = Table("Table_12", Table_12)
        var tableClassInput13 = Table("Table_13", Table_13)
        var tableClassInput14 = Table("Table_14", Table_14)

        var tableClass : ArrayList<Table> = arrayListOf(tableClassInput1,tableClassInput2,tableClassInput3,tableClassInput4,tableClassInput5,tableClassInput6,
            tableClassInput7,tableClassInput8,tableClassInput9,tableClassInput10,tableClassInput11,tableClassInput12,tableClassInput13,tableClassInput14)


        juciy_back.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        }

        tablesRef.addSnapshotListener{ snapshot ,e ->
            if(e!=null){
                return@addSnapshotListener
            }
            if (snapshot != null) {
                for(dc in snapshot!!.documentChanges){
                    when(dc.type){
                        DocumentChange.Type.ADDED->{  //액티비티 첫 실행시
                            for(i in tableClass.indices){
                                if(dc.document.id == tableClass[i].tableName && dc.document["useInfo"]==true){
                                    tableClass[i].tableCode.setCardBackgroundColor(Color.rgb(218,153,153))
                                }
                            }
                        }
                        DocumentChange.Type.MODIFIED->{   //파이어스토어 document가 변경될 시 실행됨
                            tablesRef
                                .get()
                                .addOnSuccessListener {
                                    for(i in tableClass.indices){
                                        if(dc.document.id == tableClass[i].tableName && dc.document["useInfo"]==true){
                                            tableClass[i].tableCode.setCardBackgroundColor(Color.rgb(218,153,153))
                                            tableClass[i].tableCode.isClickable=true
                                            Log.d("on","${tableClass[i].tableName}없다가 앉음")
                                        }else if(dc.document.id == tableClass[i].tableName && dc.document["useInfo"]==false){
                                            tableClass[i].tableCode.setCardBackgroundColor(Color.rgb(220,217,217))
                                            tableClass[i].tableCode.isClickable=false
                                            Log.d("off","${tableClass[i].tableName}에 앉았다가 일어남")
                                        }else{
                                            Log.d("else","암것도 아님")
                                        }
                                    }
                                }
                        }
                    }
                }
            }else{
                Log.d("snapshot","Current data : null")
            }
        }
    }
}