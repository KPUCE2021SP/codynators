package com.example.summerproject

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class CustomDialog(context: Context) {
    private val dialog = Dialog(context)
    fun dig(uid : String){
        dialog.setContentView(R.layout.dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        val btnBack = dialog.findViewById<Button>(R.id.dialog_j_cancle)
        val btnMsg = dialog.findViewById<Button>(R.id.dialog_j_msg)
        var digUID = dialog.findViewById<TextView>(R.id.dig_j_UID)
        digUID.text = uid
        btnBack.setOnClickListener{
            dialog.dismiss()
        }

        btnMsg.setOnClickListener {
            onClickListener.onClicked()
            dialog.dismiss()
        }
        dialog.show()
    }
    interface ButtonClickListener{
        fun onClicked()
    }
    private lateinit var onClickListener: ButtonClickListener

    fun setOnClickedListener(listener:ButtonClickListener){
        onClickListener =listener
    }
}