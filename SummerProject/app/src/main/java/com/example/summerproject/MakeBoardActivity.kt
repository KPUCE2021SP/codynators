package com.example.summerproject

import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.summerproject.ChatLogActivity.Companion.TAG
import com.example.summerproject.fragments.messageFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_make_board.*
import java.time.LocalDate

class
MakeBoardActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_board)

        edit_title.setText("${LocalDate.now()}")

        complete_btn.setOnClickListener { // 완료 클릭 시 일어나는
            val db: FirebaseFirestore = Firebase.firestore
            val itemsCollectionRef = db.collection("MemoList") // Collection 이름
            val builder = AlertDialog.Builder(this)

            builder.setTitle("To-Do-List")
            builder.setMessage("저장 하시겠습니까?")
            builder.setIcon(R.drawable.add_circle)


            val listener = DialogInterface.OnClickListener { _, p1 ->
                when(p1){
                    DialogInterface.BUTTON_POSITIVE -> {

                        val information = hashMapOf(
                            "text" to edit_text.text.toString() // 메모 내용 입력
                        )

                        itemsCollectionRef.document(FirebaseAuth.getInstance().uid.toString()) // firebaseAuth 객체에 따라 데이터 저장
                            .collection("Memo").document(edit_title.text.toString()) // 날짜별로 저장하기 위해서
                            .set(information)
                            //Collection("MemoList) -> Document(uid) -> Collection(edit_title) -> document("Memo") -> Field(edit_text)
                        finish()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {

                    }
                }
            }
            builder.setPositiveButton("저장", listener)
            builder.setNegativeButton("취소", listener)
            builder.show()
        }


    }
}