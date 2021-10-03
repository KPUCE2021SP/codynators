package com.example.summerproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_set_profile.*
import kotlinx.android.synthetic.main.activity_set_profile.selectphoto_imageview_set_profile
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*

//21.09.03 eemdeeks 프로필 설정
//21.09.11 eemdeeks 프로필 유무 추가
//21.10.01 eemdeeks 프로필 이미지 default 설정 추가

class SetProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_profile)

        ifUseProfile()


        selectphoto_button_set_profile.setOnClickListener {
            Log.d("RegisterActivity","Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        set_profile.setOnClickListener {
            var userName = name_edt_set_profile.text.toString()

            if (userName == ""){
                toast("Name is blank")
            }else{
                toast("Set Profile Success")

                uploadImageToFirebaseStorage()

                finish()
                overridePendingTransition(R.anim.fadein,R.anim.fadeout)

                startActivity<MainActivity>()

            }
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

            selectphoto_imageview_set_profile.setImageBitmap(bitmap)
            selectphoto_button_set_profile.alpha = 0f

            //    val bitmapDrawable = BitmapDrawable(bitmap)
            //    selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
        }

    }

    //프로필 이미지 DB에서 불러오기
    private fun uploadImageToFirebaseStorage(){
        if(selectedPhotoUri == null) {
            Log.d("SetProfileActivity", "Photo set default")
            var default_Image = "https://firebasestorage.googleapis.com/v0/b/kpu-summerproject.appspot.com/o/images%2F5beaf43d-c9cb-4beb-a1fd-1a9d0718ee96?alt=media&token=7ba5badb-b6fd-43d7-a076-7688e1f0eac8"
            saveUserToFirebaseDatabase(default_Image)

        }

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!).addOnSuccessListener {
            Log.d("SetProfileActivity", "Successfully uploaded image: ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                it.toString()
                Log.d("SetProfileActivity","File Location: $it")

                saveUserToFirebaseDatabase(it.toString())
            }

        }.addOnFailureListener{
            //do some logging here
        }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, name_edt_set_profile.text.toString(), profileImageUrl)

        ref.setValue(user).addOnSuccessListener {
            Log.d("RegisterActivity","Finally we saved the user to Firebase Database")
        }
    }

    //21.09.11 eemdeeks : 프로필 설정 유무
    private fun ifUseProfile(){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                p0.children.forEach{

                    val user = it.getValue(User::class.java)
                    if (user?.uid==uid && user?.profileImageUrl!=null){
                        finish()

                        startActivity<MainActivity>()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}