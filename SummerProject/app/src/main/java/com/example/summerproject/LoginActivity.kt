package com.example.summerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/*
* 로그인 구현  21.07.30 김태용
*  Anko 라이브러리 사용*/
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener { //로그인을 위한 버튼 리스너
            val userEmail = username.text.toString()
            val password = password.text.toString()

            doLogin(userEmail, password) // 로그인을 위한 함수
        }

        registerBtn.setOnClickListener {
            startActivity<RegisterActivity>()
            finish()
        }
    }

    private fun doLogin(userEmail : String, password : String){ // 로그인 함수
        Firebase.auth.signInWithEmailAndPassword(userEmail, password) // Firebase.auth - firebase의 인증 API를 제공하는 객체
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    startActivity<MainActivity>() // 로그인 성공시 MainActivity 실행
                    finish()
                }else{
                    Log.w("LoginActivity", "signInWithEmail", it.exception)
                    toast("로그인 실패")
                }
            }
    }
}