package com.example.optifit

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.adapter.CategoryAdapter
import com.example.optifit.models.Category
import com.example.optifit.storage.CategoryData
import com.example.optifit.storage.utility.loadCategoryVideoUrlsFromJson
import org.json.JSONObject
import java.io.IOException


class CategoriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories)

        // BACK BUTTON
        val backArrow = findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        // CATEGORIES RECYCLER VIEW
        val myDataset = CategoryData().loadCategory()
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.categoriesRecyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        categoryRecyclerView.layoutManager = layoutManager

        // Load JSON data
        val jsonString = try {
            val inputStream = resources.openRawResource(R.raw.categories)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }

        val json = JSONObject(jsonString)

        // Parse JSON data and update videoUrls in Category objects
        for (category in myDataset) {
            category.videoUrls = loadCategoryVideoUrlsFromJson(json, category.categoryTitle)
        }

        // Create and set the CategoryAdapter with item click listener
        val categoryAdapter = CategoryAdapter(this, myDataset)
        categoryAdapter.setOnItemClickListener(object : CategoryAdapter.OnItemClickListener {
            override fun onItemClick(category: Category) {
                // Now you can start the Video Activity with the category details
                val intent = Intent(this@CategoriesActivity, Video::class.java)
                intent.putExtra("categoryTitle", category.categoryTitle)
                intent.putStringArrayListExtra("videoUrls", ArrayList(category.videoUrls ?: emptyList()))
                startActivity(intent)
            }
        })

        categoryRecyclerView.adapter = categoryAdapter
    }

    }





