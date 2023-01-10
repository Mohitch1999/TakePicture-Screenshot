package com.example.screenshottake

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import org.w3c.dom.Text
import java.io.File
import kotlin.contracts.contract

class MainActivity : AppCompatActivity() {
    lateinit var imageUriForScreenshot: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var container  = findViewById<LinearLayout>(R.id.container);


        var imageview : ImageView = findViewById<ImageView>(R.id.imagebus);
        var text = findViewById<TextView>(R.id.text);
        var btnscrnsht = findViewById<Button>(R.id.screenshot);
        var btntakepic = findViewById<Button>(R.id.takepicture);

        var data = "Business is the practice of making one's living or making money by producing or buying and selling products (such as goods and services). It is also \"any activity or enterprise entered into for profit."
        text.setText(data)

        btntakepic.setOnClickListener {
            val intent = Intent(this@MainActivity,Takepicture::class.java)
            startActivity(intent)
        }


        imageUriForScreenshot=createImageUriForScreenshot()


        // for take screenshot
        btnscrnsht.setOnClickListener {
            val bitmap = getBitmapFromview(container)
            getStoreBitmap(bitmap)
        }



    }

    // for store the bitmap in the png format
    private fun getStoreBitmap(bitmap: Bitmap) {
val outputStream =  applicationContext.contentResolver.openOutputStream(imageUriForScreenshot)
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream)
        outputStream?.close()
    }


    // create image uri for the image
    private fun createImageUriForScreenshot(): Uri {
        val image = File(applicationContext.filesDir,"camera_photos.png")
        return FileProvider.getUriForFile(applicationContext,
            "com.example.screenshottake.fileProvider",
            image)
    }

    // we create a bitmap for create a drawing and put it on a canvas, which is coming from the container view
    private fun getBitmapFromview(view: View): Bitmap {
        // create a blank bitmap according to the view of container
        val bitmap = Bitmap.createBitmap(view.width,view.height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bg = view.background
        // create a background on a canvas  which is draw on a container
        bg.draw(canvas)
        // create a view on canvas which is draw on a container
        view.draw(canvas)
        return bitmap

    }
}