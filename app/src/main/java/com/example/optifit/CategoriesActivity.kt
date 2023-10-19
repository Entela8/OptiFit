package com.example.optifit

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CategoriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories)

        //BACK BUTTON
        val backArrow = findViewById<ImageView>(R.id.backArrow)
        // Set an OnClickListener for the ImageView
        backArrow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        //CATEGORIES RECYCLER VIEW
        val myDataset = CategoryData().loadCategory()
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.categoriesRecyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        categoryRecyclerView.layoutManager = layoutManager
        categoryRecyclerView.adapter = CategoryAdapter(this, myDataset)
    }
}