package com.es.k_retrofit_image_upload

import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.es.k_retrofit_image_upload.databinding.ActivityMainBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var fileUri: Uri
    private lateinit var binding: ActivityMainBinding
    val BASEURL = "https://image-upload-api-retrofit.herokuapp.com/"
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                fileUri = data?.data!!

                binding.showImageOffline.setImageURI(fileUri)


            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.showImageOffline.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }


    }


    fun upload() {


        val filesDir = applicationContext.filesDir
        val file = File(filesDir, "image.png")

        val inputStream = contentResolver.openInputStream(fileUri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

        inputStream.close()
        outputStream.close()

    }

}