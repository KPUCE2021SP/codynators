package com.example.summerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

//21.08.16 eemdeeks : 새로운 메시지 보내는 액티비티
class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select User"

  /*      val adapter = GroupAdapter<ViewHolder>()

        adapter.add(UserItem())
        adapter.add(UserItem())
        adapter.add(UserItem())

        recyclerview_newmessage.adapter = adapter */

        fetchUsers()
    }

    companion object {
        val USER_KEY = "USER_KEY"
    }

    private fun fetchUsers(){
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach{
                    Log.d("NewMessage",it.toString())
                    val user = it.getValue(User::class.java)
                    if (user!=null){
                        adapter.add(UserItem(user))
                    }
                }
                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem

                    val intent = Intent(view.context,ChatLogActivity::class.java)
                    //intent.putExtra(USER_KEY,userItem.user.username)
                    intent.putExtra(USER_KEY,userItem.user)
                    startActivity(intent)

                    finish()

                }
                recyclerview_newmessage.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}

class UserItem(val user: User): Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.username_textview_new_message.text = user.username

        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageView_new_message)
    }
    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}