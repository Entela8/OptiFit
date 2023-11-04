package com.example.optifit

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.optifit.models.Profile
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import android.Manifest

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
        backArrow.setOnClickListener(object : View.OnClickListener
        {
            override fun onClick(v: View?)
            {
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedProfilePhotoUri = data.data
            // Set the selected image to the ImageView
            profilePhotoImageView.setImageURI(selectedProfilePhotoUri)
        }
    }
    // Save button click event handler
    fun onSaveClicked(view: View)
    {
        // Retrieve user input from EditText fields
        val name = editName.text.toString()
        val age = editAge.text.toString()
        val description = editDescription.text.toString()
        val imageURI = selectedProfilePhotoUri

        // Create a JSON object to store user data
        val userData = JSONObject()

        // Check if a profile photo URI is available and save it
        if (selectedProfilePhotoUri != null) {
            userData.put(Profile.PHOTO, selectedProfilePhotoUri.toString())
        }

        userData.put(Profile.NAME, name)
        userData.put(Profile.AGE, age)
        userData.put(Profile.PHOTO, selectedProfilePhotoUri)
        userData.put(Profile.DESCRIPTION, description)

        // Save the JSON object to a file
        val file = File(filesDir, "user_profile.json")
        val outputStream = FileOutputStream(file)
        outputStream.write(userData.toString().toByteArray())
        outputStream.close()
        Toast.makeText(this, "Profil sauvegardé avec succès", Toast.LENGTH_SHORT).show()
    }

    private fun loadProfileData()
    {
        val file = File(filesDir, "user_profile.json")

        if (file.exists()) {
            val json = file.readText()
            if (json.isNotEmpty()) {
                val userData = JSONObject(json)
                // Update EditText fields with loaded data
                editName.setText(userData.optString("name"))
                editAge.setText(userData.optString("age"))
                editDescription.setText(userData.optString("description"))
                Log.d("PHOTO", userData.optString("photo"))
               // profilePhotoImageView.setImageURI(userData.optString("photo").toUri())
            }
        }
    }

}