package com.tbalogh.i420test

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ColorSpace
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val options = BitmapFactory.Options()
        options.outWidth = 100
        options.outHeight = 100
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val bm = BitmapFactory.decodeResource(resources, android.R.drawable.ic_delete, options)

        val rowBytes = bm.rowBytes

        imageI420.setImageBitmap(bm)
    }
}
