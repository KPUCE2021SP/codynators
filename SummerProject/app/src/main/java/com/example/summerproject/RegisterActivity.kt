package com.example.summerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/*
* 회원가입 구현  21.07.30 김태용 afterschool -weekely
* Anko 라이브러리 사용*/
class RegisterActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth // // FireBase Auth 객체를 얻는 인스턴스

        Register.setOnClickListener { //회원가입
            var userEmail = Email.text.toString()
            var password = PWD.text.toString()

            doSignUp(userEmail, password) // 회원가입 위한 함수
        }
        btnCancel.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }
    }
    private fun doSignUp(userEmail: String, password: String){ // 회원가입을 위한 함수
        auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    startActivity<MainActivity>()
                    finish()
                    overridePendingTransition(R.anim.fadein,R.anim.fadeout)
                }else{
                    Log.w("LoginActivity", "signInWithEmail", it.exception)
                    toast("회원가입 실패 비밀번호 6자리 이상 입력하세요!")
                }
            }
    }
}