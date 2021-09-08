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
import com.kakao.sdk.common.util.Utility


/*
* 로그인 구현  21.07.30 김태용
*  Anko 라이브러리 사용
* 홈액티비티로 기능 이동*/
class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username.bringToFront()
        password.bringToFront()

        val auth = Firebase.auth
        firebaseAuth = FirebaseAuth.getInstance() // Firebase Auth 객체를 얻는 변수

        val TAG = "LoginActivity"
        val keyHash = Utility.getKeyHash(this)
        Log.v(TAG, keyHash)

        loginBtn.setOnClickListener { //로그인을 위한 버튼 리스너
            var userEmail = username.text.toString()
            var password = password.text.toString()

            if(userEmail == "" || password == ""){
                toast("E-mail or Password is blank")
            }else{
                var create = auth.signInWithEmailAndPassword(userEmail, password)
                create.addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        startActivity<MainActivity>() // 로그인 성공시 MainActivity 실행
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                    } else {
                        toast("Login Failed")
                    }
                }
            }
        }

        registerBtn.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        }
    }
}