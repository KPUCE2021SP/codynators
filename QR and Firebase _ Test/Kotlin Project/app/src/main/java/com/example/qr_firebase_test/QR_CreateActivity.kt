package com.example.qr_firebase_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class QR_CreateActivity : AppCompatActivity() {
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.getReference("Table Use Information")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        var Table : Table_Data = Table_Data("", 0)
        var Map : HashMap<String, Any> = HashMap<String, Any>()
        Map.put("Table 1", Table)

        myRef.updateChildren(Map)
        finish()
    }
}