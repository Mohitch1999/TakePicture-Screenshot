package com.example.screenshottake

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File

class Takepicture : AppCompatActivity() {

    lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_takepicture)

        var imageview : ImageView = findViewById<ImageView>(R.id.imagebus);
        var btntakepic = findViewById<Button>(R.id.takepicture);


        // with the help of contract we can open the camera and take the picture of it and then we can set on an imageview
        val contract = registerForActivityResult(ActivityResultContracts.TakePicture()){
            imageview.setImageURI(null)
            imageview.setImageURI(imageUri)
        }

        imageUri=createImageUri()

        // for take picture
        btntakepic.setOnClickListener {
            //launch the camera
            contract.launch(imageUri)
        }

    }
    // create image uri for the image
    private fun createImageUri(): Uri {
        val image = File(applicationContext.filesDir,"camera_photos")
        return FileProvider.getUriForFile(applicationContext,
            "com.example.screenshottake.fileProvider",
            image)
    }
}