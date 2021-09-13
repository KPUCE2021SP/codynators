package com.example.summerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_latest_messages.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.latest_message_row.view.*

//21.08.16 eemdeeks : 최근 메시지 액티비티
//21.09.09 eemdeeks : 리싸이클러 뷰 이용한 최근 메시지 보이기
//21.09.10 eemdeeks : 아이템 클릭시 해당 채팅방으로 들어가기
class LatestMessagesActivity : AppCompatActivity() {

    companion object{
        var currentUser: User? = null
        val TAG = "LatestMessages"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)


        recyclerview_latest_messages.adapter = adapter //리싸이클러뷰에 마지막 메세지 연결

        //리싸이클러뷰 아이템마다 줄그어서 구분
        recyclerview_latest_messages.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        //set item click listener on your adapter
        adapter.setOnItemClickListener { item, view ->
            Log.d(TAG,"item tag")

            val intent = Intent(this, ChatLogActivity::class.java)

            // we are missing the chat partner user

            val row = item as LatestMessageRow

            intent.putExtra(NewMessageActivity.USER_KEY, row.chatPartnerUser)
            startActivity(intent)
        }

        new_message.setOnClickListener {
            val intent = Intent(this,NewMessageActivity::class.java)
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
            startActivity(intent)
        }

        message_clear.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        }

//        setupDummyRows()  //리싸이클러 뷰 보이기

        listenForLatestMessages()

        fetchCurrentUser()

        verifyUserIsLoggedIn()

    }

    val latestMessageMap = HashMap<String, ChatMessage>()


    private fun refreshRecyclerViewMessages(){
        adapter.clear()
        latestMessageMap.values.forEach{
            adapter.add(LatestMessageRow(it))
        }
    }

    //RDB에 latest-messages 관련
    private fun listenForLatestMessages(){
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")

        ref.addChildEventListener(object : ChildEventListener{
            //RDB 새로 생겼을 시
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return

                latestMessageMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            //RDB 변경시 ...
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return

                latestMessageMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    val adapter = GroupAdapter<ViewHolder>()

    //리사이클러 뷰
//    private fun setupDummyRows(){
//        val adapter = GroupAdapter<ViewHolder>()
//
//        adapter.add(LatestMessageRow())
//        adapter.add(LatestMessageRow())
//        adapter.add(LatestMessageRow())
//
//        recyclerview_latest_messages.adapter = adapter //리싸이클러뷰에 마지막 메세지 연결
//
//    }

    //유저 아이디 가져와서
    private fun fetchCurrentUser(){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{

            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)
                Log.d("LatestMessages", "Current user ${currentUser?.profileImageUrl}")
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    //툴바에 아이템들
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.menu_new_message ->{
                val intent = Intent(this,NewMessageActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_sign_out ->{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}