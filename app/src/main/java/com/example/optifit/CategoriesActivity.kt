package com.example.optifit

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class CategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories)


        //BACK BUTTON
        val backArrow = findViewById<ImageView>(R.id.backArrow)

        // Set an OnClickListener for the ImageView
        backArrow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // Handle the back button click event here
                finish() // Finish the current activity to go back
            }
        })
    }
}