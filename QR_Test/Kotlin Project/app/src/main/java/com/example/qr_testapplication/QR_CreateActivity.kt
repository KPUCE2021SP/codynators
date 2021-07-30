package com.example.qr_testapplication

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_create.*
import java.lang.Exception

class QR_CreateActivity : AppCompatActivity() {
    private val text : String = "https://alkorithm.tistory.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val multiFormatWriter = MultiFormatWriter()

        try {
            val bitMatrix : BitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap : Bitmap = barcodeEncoder.createBitmap((bitMatrix))
            qrcode.setImageBitmap(bitmap)
        }catch(e: Exception){}
    }
}