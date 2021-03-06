package com.example.summerproject

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.summerproject.databinding.ActivityMainBinding
import com.example.summerproject.fragments.messageFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(){
    private lateinit var mBinding: ActivityMainBinding
    // 2021.08.01 khsexk: rdb 연동
//    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
//    val myRef : DatabaseReference = database.getReference("Table Use Information") made by 현석
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater) // activity_main.xml 바인딩위한 변수
        setContentView(mBinding.root)
        //네비게이션들을 담는 호스트
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment

        //네비게이션 컨트롤러
        val navController = navHostFragment.navController

        //바텀 네비게이션뷰와 네비게이션을 묶어준다.
        NavigationUI.setupWithNavController(mBinding.myBottomNav, navController)

        //FCM
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // 새로운 FCM 등록 토큰 얻기위해
            val token = task.result

            // 로그와 토스트로 찾기
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d("FCM", msg)
            //Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
        })

//        storage = Firebase.storage
//
//        var uid = FirebaseAuth.getInstance().uid.toString()
//        var userDB = Firebase.database.getReference("users")
//        var imgUrl = userDB.child(uid).child("profileImageUrl").toString()
//
//        var imgRef = storage.getReferenceFromUrl(imgUrl)
//
//        displayImageRef(imgRef,header_user_img)





        /*네비게이션 수정 by 태용*/
//        val toolbar : Toolbar = findViewById(R.id.toolbar) // toolBar를 통해 App Bar 생성
//        setSupportActionBar(toolbar)
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.navi_menu) // 홈버튼 이미지 변경
//        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
//
//        //네비게이션 드로어 생성
//        drawerLayout = drawer_layout
//
//        //네비게이션 드로어 내에 있는 화면의 이벤트를 처리하기 위해 생성
//        navigationView = nav_view
//        navigationView.setNavigationItemSelectedListener(this) // navigation 리스너

        // 2021.08.01 khsexk: rdb 초기 데이터 생성 -> 생성후 삭제코드
//        var Table : TableData = TableData("", 0)
//        var Map : HashMap<String, Any> = HashMap<String, Any>()
//        Map.put("Table 1", Table)
//        Map.put("Table 2", Table)
//        Map.put("Table 3", Table)
//        myRef.updateChildren(Map) made by 현석

        /*
        /*Cloud Firestore 로 변경하기 위해 작업*/
        val db: FirebaseFirestore = Firebase.firestore // : written by 태용 // : 수정(현석)
        val array : ArrayList<String> = arrayListOf("Table_1", "Table_2", "Table_3"
            , "Table_4", "Table_5", "Table_6", "Table_7", "Table_8", "Table_9", "Table_10"
            , "Table_11", "Table_12", "Table_13", "Table_14") // 테이블 3개로 테스트 : written by 태용

        val itemsCollectionRef = db.collection("Table_Use_Information") // Collection 이름 : written by 태용
//
        val itemMap = hashMapOf( // to 앞의 값은 Key, to 뒤의 값은 value : written by 태용
            "userId" to "",
            "useInfo" to false,
        )

        //board.bringToFront()

        for(it in array){ // "Table use Information"이라는 컬렉션 밑에  table1, table2, table3 document 추가하고 각 document에 key : value 삽입
            // : written by 태용
            itemsCollectionRef.document(it)
                .set(itemMap)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!")  }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
        */



        // 2021.08.01 khsexk: 체크인 and 체크아웃
//        checkIn.setOnClickListener{
//            startActivity<CheckInActivity>()
//            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
//        }
//        checkOut.setOnClickListener {
//            startActivity<CheckOutActivity>()
//            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
//        }
//
//        placeList.setOnClickListener {
//            startActivity<MapActivity>()
//            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
//        }

    }


    /*네비게이션 수정으로 인한 주석 처리 by 태용*/
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        //클릭한 툴바 메뉴 아이템 id마다 다르게 실행하도록 설정
//        when(item.itemId){
//            android.R.id.home->{
//                drawerLayout.openDrawer(GravityCompat.START)
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    // 드로어 내 아이템 클릭 이벤트 처리하는 함수
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.menu_item1-> {
//                startActivity<CheckInActivity>()
//                overridePendingTransition(R.anim.fadein,R.anim.fadeout)
//            }
//            R.id.menu_item2-> {
//                startActivity<CheckOutActivity>()
//                overridePendingTransition(R.anim.fadein,R.anim.fadeout)
//            }
//            R.id.menu_item3-> {
//                startActivity<MapActivity>()
//                overridePendingTransition(R.anim.fadein,R.anim.fadeout)
//            }
//        }
//        return false
//    }


    private fun displayImageRef(imageRef: StorageReference?, view: ImageView) {
        imageRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            view.setImageBitmap(bmp)
        }?.addOnFailureListener {
// Failed to download the image
        }
    }
}