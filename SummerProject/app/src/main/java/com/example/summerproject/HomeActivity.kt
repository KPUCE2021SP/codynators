package com.example.summerproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import com.kakao.sdk.common.util.Utility
import kotlinx.android.synthetic.main.activity_home.*
import android.content.pm.PackageManager

import android.content.pm.PackageInfo
import android.util.Base64
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class HomeActivity: AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient // 구글 로그인 연동을 위한 변수
    private lateinit var callbackManager: CallbackManager // 페이스북 로그인 연동을 위한 콜백변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //getHashKey()


        val auth = Firebase.auth
        callbackManager = CallbackManager.Factory.create() // 페이스북 위한 콜백 매니저
        firebaseAuth = FirebaseAuth.getInstance() // Firebase Auth 객체를 얻는 변수

        val TAG = "HomeActivity"
        val keyHash = Utility.getKeyHash(this)
        Log.v(TAG, keyHash)

        // 카카오 로그인 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                toast("로그인 실패")
                //로그인 실패시
            }
            else if (token != null) {
                //로그인 성공시
                toast("로그인 성공")
                startActivity<MainActivity>()
            }
        }

        //페북
        login_button.setReadPermissions("email", "public_profile")
        login_button.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken) // 로그인 성공시 handleFacebookAccessToken 함수 실행
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_web_client_id)) // res -> values -> strings.xml 파일 확인
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        //
       doLogin.setOnClickListener {
           startActivity<LoginActivity>()
           overridePendingTransition(R.anim.fadein,R.anim.fadeout)
       }

        doRegister.setOnClickListener {
            startActivity<RegisterActivity>()
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        }

        googleBtn.setOnClickListener{ // 구글 버튼 로그인
            signIn()
        }

        KakaoLogin.setOnClickListener{ // 카카오 간편 로그인
            LoginClient.instance.run {
                if (isKakaoTalkLoginAvailable(this@HomeActivity)) {
                    loginWithKakaoTalk(this@HomeActivity, callback = callback)
                } else {
                    loginWithKakaoAccount(this@HomeActivity, callback = callback)
                }
            }
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

//    private fun getHashKey() {
//        var packageInfo: PackageInfo? = null
//        try {
//            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }
//        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
//        for (signature in packageInfo!!.signatures) {
//            try {
//                val md: MessageDigest = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
//            } catch (e: NoSuchAlgorithmException) {
//                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
//            }
//        }
//    }


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

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) { // 페이스북과 firebase 연동
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential) // 구글 계정으로 연동되어
            .addOnCompleteListener(this) { task -> // 성공시
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    updateUI(user) // Main화면으로 넘어감
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
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
        private const val TAG = "GoogleActivity&FacebookActivity"
        private const val RC_SIGN_IN = 9001
    }
}