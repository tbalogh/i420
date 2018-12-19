package com.tbalogh.i420test

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


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

        val bitmap = BitmapFactory.decodeResource(resources, android.R.drawable.ic_delete, options)
        val rgbaBuffer = argb2rgba(bitmap)
        setupImageView(rgbaBuffer, bitmap)

        openGlStuff(rgbaBuffer, bitmap.width, bitmap.height)

    }

    private fun openGlStuff(data: Buffer, width: Int, height: Int) {
        val textureIds = IntArray(1)
        GLES20.glGenTextures(1, textureIds, 0)

        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height,
                0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, data)

    }

    private fun argb2rgba(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocate(bitmap.height * bitmap.rowBytes)
        byteBuffer.order(ByteOrder.BIG_ENDIAN)
        val ib = byteBuffer.asIntBuffer()

        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        for (i in pixels.indices) {
            ib.put(pixels[i] shl 8 or pixels[i].ushr(24))
        }

//        bitmap.recycle()

        byteBuffer.position(0)
        return byteBuffer
    }

    private fun intArrayFromByteBuffer(buffer: ByteBuffer): IntArray {
        val intBuf = ByteBuffer.wrap(buffer.array())
                .order(ByteOrder.BIG_ENDIAN)
                .asIntBuffer()
        val intArray = IntArray(intBuf.remaining())
        intBuf.get(intArray)
        return intArray
    }

    private fun setupImageView(argbBuffer: ByteBuffer, bitmap: Bitmap) {
        val config2 = Bitmap.Config.ARGB_8888
        val bm:Bitmap = Bitmap.createBitmap(intArrayFromByteBuffer(argbBuffer), bitmap.width,bitmap.height, config2)
        imageI420.setImageBitmap(bm)
    }
}
