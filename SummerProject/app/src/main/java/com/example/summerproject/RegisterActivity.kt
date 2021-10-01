package com.example.summerproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast
import java.util.*

/*
* 회원가입 구현  21.07.30 김태용 afterschool -weekely
* RDB에 회원 정보 저장(이름,이미지추가) 21.08.13 - eemdeeks
* image default 설정과 이름 비었을 경우 추가    21.10.01 - eemdeeks
* Anko 라이브러리 사용*/

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth // // FireBase Auth 객체를 얻는 인스턴스


        Register.setOnClickListener { //회원가입
            var userName = name_edt_register.toString()
            var userEmail = Email.text.toString()
            var password = PWD.text.toString()

            if(userEmail == "" || password == ""){
                toast("E-mail or Password is blank")
            }else if(userName == ""){
                toast("Name is blank")
            }
            else{
                var create = auth.createUserWithEmailAndPassword(userEmail,password)
                create.addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        toast("Register Success")

                        uploadImageToFirebaseStorage() //프로필 이미지 RDB에 저장함수

                        finish()
                        overridePendingTransition(R.anim.fadein,R.anim.fadeout)
                    } else {
                        toast("Register Failed. Password must be at least 6")
                    }
                }
            }

            //doSignUp(userEmail, password) // 회원가입 위한 함수
        }

        //프로필 이미지 선택 버튼 구현
        selectphoto_button_register.setOnClickListener {
            Log.d("RegisterActivity","Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }


        btnCancel.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }

    }

    var selectedPhotoUri: Uri? = null   //프로필 이미지 uri

    //선택한 프로필 이미지 activity에 보이기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==0 && resultCode == Activity.RESULT_OK && data !=null){

            Log.d("RegisterActivity","Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)

            selectphoto_imageview_register.setImageBitmap(bitmap)
            selectphoto_button_register.alpha = 0f

            //    val bitmapDrawable = BitmapDrawable(bitmap)
            //    selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
        }

    }

    //프로필 이미지 DB에서 불러오기
    private fun uploadImageToFirebaseStorage(){
        if(selectedPhotoUri == null) {
            Log.d("RegisterActivity", "Photo set default")
            var default_Image = "https://firebasestorage.googleapis.com/v0/b/kpu-summerproject.appspot.com/o/images%2F5beaf43d-c9cb-4beb-a1fd-1a9d0718ee96?alt=media&token=7ba5badb-b6fd-43d7-a076-7688e1f0eac8"
            saveUserToFirebaseDatabase(default_Image)

        }

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!).addOnSuccessListener {
            Log.d("RegisterActivity", "Successfully uploaded image: ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                it.toString()
                Log.d("RegisterActivity","File Location: $it")

                saveUserToFirebaseDatabase(it.toString())
            }

        }.addOnFailureListener{
            //do some logging here
        }
    }

    //이름, 아이디, 비밀번호, 프로필 이미지 RDB에 저장 함수
    private fun saveUserToFirebaseDatabase(profileImageUrl: String){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, name_edt_register.text.toString(), profileImageUrl)

        ref.setValue(user).addOnSuccessListener {
            Log.d("RegisterActivity","Finally we saved the user to Firebase Database")
        }
    }
}
