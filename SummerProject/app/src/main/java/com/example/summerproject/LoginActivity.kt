package com.example.summerproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/*
* 로그인 구현  21.07.30 김태용
*  Anko 라이브러리 사용*/
class LoginActivity : AppCompatActivity() {
<<<<<<< HEAD
    private var firebaseAuth: FirebaseAuth? = null

=======
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
>>>>>>> origin/Taeyong-Kim
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance() // Firebase Auth 객체를 얻는 변수

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.firebase_web_client_id)) // res -> values -> strings.xml 파일 확인
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        loginBtn.setOnClickListener { //로그인을 위한 버튼 리스너
            val userEmail = username.text.toString()
            val password = password.text.toString()


            doLogin(userEmail, password) // 로그인을 위한 함수
        }

        registerBtn.setOnClickListener {
            startActivity<RegisterActivity>()
            finish()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }

        googleBtn.setOnClickListener{
            signIn()
        }
    }

//    override fun onStart() { // 액티비티 주기 단계 중 -> 계정 존재 시 -> 바로 MainActivity로 진행
//        super.onStart()
//        val currentUser = firebaseAuth.currentUser
//        updateUI(currentUser)
//    }


    // 구글 간편로그인 함수
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    // onActivityResult는 main액티비티에서 sub액티비티를 호출하여 넘어갔다가, 다시 main 액티비티로 돌아올때 사용되는 기본 메소드 이다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) { // 로그인 성공시
                        Log.d(TAG, "signInWithCredential:success")
                        val user = firebaseAuth.currentUser
                        updateUI(user)
                    } else { // 로그인 실패시
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        updateUI(null)
                    }
                }
    }

    private fun updateUI(user: FirebaseUser?) { // user 존재시 MainActivity로 넘어가는 함수
        if(user != null){
            toast("로그인 성공")
            startActivity<MainActivity>()
        }
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }


    private fun doLogin(userEmail: String, password: String){ // 로그인 함수
        firebaseAuth.signInWithEmailAndPassword(userEmail, password) // Firebase.auth - firebase의 인증 API를 제공하는 객체
            .addOnCompleteListener(this){
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