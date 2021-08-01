package com.example.summerproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/*
* 로그인 구현  21.07.30 김태용
*  Anko 라이브러리 사용*/
class LoginActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance() // Firebase Auth 객체를 얻는 변수

        loginBtn.setOnClickListener { //로그인을 위한 버튼 리스너
            var userEmail = username.text.toString()
            var password = password.text.toString()


            doLogin(userEmail, password) // 로그인을 위한 함수
        }

        registerBtn.setOnClickListener {
            startActivity<RegisterActivity>()
            finish()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }
    }

    private fun doLogin(userEmail: String, password: String){ // 로그인 함수
        firebaseAuth?.signInWithEmailAndPassword(userEmail, password) // Firebase.auth - firebase의 인증 API를 제공하는 객체
            ?.addOnCompleteListener(this){
                if(it.isSuccessful){
                    startActivity<MainActivity>() // 로그인 성공시 MainActivity 실행
                    finish()
                    overridePendingTransition(R.anim.fadein,R.anim.fadeout)
                }else{
                    Log.w("LoginActivity", "signInWithEmail", it.exception)
                    toast("로그인 실패")
                }
            }
    }
}