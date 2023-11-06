package com.example.optifit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.optifit.models.Profile
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

class ProfileActivity : ComponentActivity() {

    private lateinit var editName: EditText
    private lateinit var editAge: EditText
    private lateinit var editDescription: EditText
    private lateinit var profilePhotoImageView: ImageView
    private var selectedProfilePhotoUri: Uri? = null

    private val PERMISSION_CODE = 1001
    private val FILE_NAME = "profile_photo.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        // BACK BUTTON
        val backArrow = findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        editName = findViewById(R.id.editName)
        editAge = findViewById(R.id.editAge)
        editDescription = findViewById(R.id.editDescription)
        profilePhotoImageView = findViewById(R.id.profilePicture)

        profilePhotoImageView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    openGallery()
                } else {
                    val manageStorageIntent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                    startActivity(manageStorageIntent)
                }
            } else {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        PERMISSION_CODE
                    )
                } else {
                    openGallery()
                }
            }
        }
        // Load the saved data from the JSON file
        loadProfileData()
    }

    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                profilePhotoImageView.setImageURI(imgUri)
                selectedProfilePhotoUri = imgUri
                if (imgUri != null) {
                    applyCircularMaskToImageView(profilePhotoImageView)
                }
            }
        }

    private fun applyCircularMaskToImageView(imageView: ImageView) {
        val circularMask = ContextCompat.getDrawable(this, R.drawable.circular_mask)
        val layers = LayerDrawable(arrayOf(circularMask, imageView.drawable))
        imageView.setImageDrawable(layers)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults) // Call super method
    }

    private fun openGallery() {
        val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        changeImage.launch(pickImg)
    }

    fun onSaveClicked(view: View) {
        // Retrieve user input from EditText fields
        val name = editName.text.toString()
        val age = editAge.text.toString()
        val description = editDescription.text.toString()

        val userData = JSONObject()
        userData.put(Profile.NAME, name)
        userData.put(Profile.AGE, age)
        userData.put(Profile.DESCRIPTION, description)

        if (selectedProfilePhotoUri != null) {
            val photoFile = File(filesDir, FILE_NAME)
            val sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("profilePhotoUri", selectedProfilePhotoUri.toString())
            editor.apply()
            try {
                val inputStream = contentResolver.openInputStream(selectedProfilePhotoUri!!)
                if (inputStream != null) {
                    val outputStream = FileOutputStream(photoFile)
                    inputStream.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                    userData.put(Profile.PHOTO, photoFile.toUri().toString())
                } else {
                    Toast.makeText(this, "Erreur en sauvegardant la photo selectionnée", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Save the JSON object to a file
        val file = File(filesDir, "user_profile.json")
        val outputStream = FileOutputStream(file)
        outputStream.write(userData.toString().toByteArray())
        outputStream.close()
        Toast.makeText(this, "Profil sauvegardé avec succès", Toast.LENGTH_SHORT).show()
    }

    private fun loadProfileData() {
        val file = File(filesDir, "user_profile.json")

        if (file.exists()) {
            val json = file.readText()
            if (json.isNotEmpty()) {
                val userData = JSONObject(json)
                // Update EditText fields with loaded data
                editName.setText(userData.optString(Profile.NAME))
                editAge.setText(userData.optString(Profile.AGE))
                editDescription.setText(userData.optString(Profile.DESCRIPTION))
                val photoUri = userData.optString(Profile.PHOTO)
                if (photoUri.isNotEmpty()) {
                    profilePhotoImageView.setImageURI(photoUri.toUri())
                    selectedProfilePhotoUri = Uri.parse(photoUri)
                }
            }
        }
    }
}