package com.example.optifit

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import com.example.optifit.R
import com.example.optifit.models.Profile
import com.example.optifit.storage.ProfileStorage
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class ProfileActivity: ComponentActivity() {

    private lateinit var editName: EditText
    private lateinit var editAge: EditText
    private lateinit var editDescription: EditText
    private lateinit var profilePhotoImageView: ImageView
    private var selectedProfilePhotoUri: Uri? = null

    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        //BACK BUTTON
        val backArrow = findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                finish()
            }
        })

        profilePhotoImageView = findViewById(R.id.profilePicture)
        editName = findViewById(R.id.editName)
        editAge = findViewById(R.id.editAge)
        editDescription = findViewById(R.id.editDescription)

        val choosePhotoButton = findViewById<ImageView>(R.id.profilePicture)
        choosePhotoButton.setOnClickListener {
            openImageChooser()
        }

        // Load the saved data from the JSON file
        loadProfileData()
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Handle the result of the image selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedProfilePhotoUri = data.data
            // Set the selected image to the ImageView
            profilePhotoImageView.setImageURI(selectedProfilePhotoUri)
        }
    }



    // Save button click event handler
    fun onSaveClicked(view: View) {
        // Retrieve user input from EditText fields
        val name = editName.text.toString()
        val age = editAge.text.toString()
        val description = editDescription.text.toString()

        // Create a JSON object to store user data
        val userData = JSONObject()

        // Check if a profile photo URI is available and save it
        if (selectedProfilePhotoUri != null) {
            userData.put(Profile.PHOTO, selectedProfilePhotoUri.toString())
        }

        userData.put(Profile.NAME, name)
        userData.put(Profile.AGE, age)
        userData.put(Profile.DESCRIPTION, description)

        // Save the JSON object to a file
        val file = File(filesDir, "user_profile.json")
        val outputStream = FileOutputStream(file)
        outputStream.write(userData.toString().toByteArray())
        outputStream.close()

        // Inform the user that the data has been saved, e.g., by displaying a message
        // or by using a Toast.
    }


    private fun loadProfileData() {
        val file = File(filesDir, "user_profile.json")

        if (file.exists()) {
            val json = file.readText()
            if (json.isNotEmpty()) {
                val userData = JSONObject(json)

                // Update EditText fields with loaded data
                editName.setText(userData.optString("name"))
                editAge.setText(userData.optString("age"))
                editDescription.setText(userData.optString("description"))
            }
        }
    }

}