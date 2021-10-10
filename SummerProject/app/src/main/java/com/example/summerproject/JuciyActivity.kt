package com.example.summerproject

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.summerproject.NewMessageActivity.Companion.USER_KEY
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_checkin.*
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.activity_juciy.*
import kotlinx.android.synthetic.main.dialog.*

class JuciyActivity : AppCompatActivity() {

    private val db: FirebaseFirestore = Firebase.firestore
    private val tablesRef = db.collection("Table_Use_Information")
    private val itemsRef = Firebase.database.getReference("users")

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
                        DocumentChange.Type.ADDED->{  //액티비티 첫 실행시( 맨 처음 다 받아와서 한번만 작동함)
                            for(i in tableClass.indices){
                                if(dc.document.id == tableClass[i].tableName && dc.document["useInfo"]==true){
                                    tableClass[i].tableCode.setCardBackgroundColor(Color.rgb(218,153,153))
                                    tableClass[i].tableCode.setOnClickListener {
                                        itemsRef.child(dc.document["userId"].toString()).child("username").get()
                                            .addOnSuccessListener {
                                                val dig = CustomDialog(this)    //다이얼로그 클래스로 만듬
                                                dig.dig(it.value.toString())   //다이얼로그에 닉네임으로 띄움
                                                dig.setOnClickedListener(object : CustomDialog.ButtonClickListener{     //메세지 버튼 클릭
                                                    override fun onClicked() {

                                                        getToUser(dc.document["userId"].toString()) //체팅으로 넘어가기

                                                    }
                                                })
                                            }
                                    }
                                }
                            }
                        }
                        DocumentChange.Type.MODIFIED->{   //파이어스토어 document가 변경될 시 실행됨(이후에 변경되는 데이터 생길시 요기서 작동)
                            tablesRef
                                .get()
                                .addOnSuccessListener { documents->
                                    for(i in tableClass.indices){
                                        if(dc.document.id == tableClass[i].tableName && dc.document["useInfo"]==true){
                                            tableClass[i].tableCode.setCardBackgroundColor(Color.rgb(218,153,153))
                                            tableClass[i].tableCode.isClickable=true
                                            tableClass[i].tableCode.setOnClickListener {
                                                itemsRef.child(dc.document["userId"].toString()).child("username").get()
                                                    .addOnSuccessListener {
                                                        val dig = CustomDialog(this)    //다이얼로그 클래스로 만듬
                                                        dig.dig(it.value.toString())   //다이얼로그에 닉네임으로 띄움
                                                        dig.setOnClickedListener(object : CustomDialog.ButtonClickListener{     //메세지 버튼 클릭
                                                            override fun onClicked() {

                                                                getToUser(dc.document["userId"].toString()) //체팅으로 넘어가기

                                                            }
                                                        })
                                                    }
                                            }
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
//        var tableUID : String =""
//        for(i in tables.indices){
//            tables[i].setOnClickListener {
//                tablesRef.addSnapshotListener { snapshot, e ->
//                    if(e != null){
//                        return@addSnapshotListener
//                    }
//                    if(snapshot != null){
//                        for(dc in snapshot!!.documentChanges){
//                            when(dc.type){
//                                DocumentChange.Type.ADDED-> {
//                                    if(dc.document.id == tableClass[i].tableName){
//                                        tableUID = dc.document["userId"].toString()
//                                        val dig =CustomDialog(this)
//                                        dig.dig(tableUID)
//                                        dig.setOnClickedListener(object : CustomDialog.ButtonClickListener{
//                                            override fun onClicked() {
//                                                //승찬아 여기를 너가 UID 받은걸로 메세지 보내기 액티비티 이동해줘야행
//                                            }
//                                        })
//                                    }
//                                }
//                                DocumentChange.Type.MODIFIED->{}
//                            }
//                        }
//                    }else{
//
//                    }
//
//                }
//                val dig = CustomDialog(this)
//                Log.d("uid","$tableUID")
//                dig.dig(tableUID)
//                dig.setOnClickedListener(object : CustomDialog.ButtonClickListener{
//                    override fun onClicked() {
//                        //승찬아 여기를 너가 UID 받은걸로 메세지 보내기 액티비티 이동해줘야행
//                    }
//                })
//            }
//        }
    }

    //자리 사용중인 사람 입력 받고 user class로 스냅샷 찍은 뒤
    //채팅으로 넘어가기까지
    //eemdeeks 21.10.10
    private fun getToUser(uid : String){

        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val ToUser  = p0.getValue(User::class.java)
                Log.d("JuciyActivity", "getToUser /${ToUser?.username}")

                val intent = Intent(this@JuciyActivity,ChatLogActivity::class.java)
                //intent.putExtra(USER_KEY,userItem.user.username)
                intent.putExtra(USER_KEY,ToUser)
                startActivity(intent)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}