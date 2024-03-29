package com.example.optifit

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.adapter.CategoryAdapter
import com.example.optifit.services.ApiService
import org.json.JSONObject


class CategoriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories)

        // BACK BUTTON
        val backArrow = findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener { finish() }

        // CATEGORIES RECYCLER VIEW
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.categoriesRecyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        categoryRecyclerView.layoutManager = layoutManager

        val apiService = ApiService(this)
        apiService.get { result ->
            val responseObj : JSONObject = result

            val categoryAdapter = CategoryAdapter(responseObj)
            categoryAdapter.setOnItemClickListener(object : CategoryAdapter.OnItemClickListener
            {
                override fun onItemClick(categoryName: String, category: JSONObject)
                {
                    val videoUrlsArray = category.getJSONArray("videoUrls")
                    val arrayList: ArrayList<String> = ArrayList()

                    for (i in 0 until videoUrlsArray.length()) {
                        val videoUrl = videoUrlsArray.getString(i)
                        arrayList.add(videoUrl)
                    }

                    val intent = Intent(this@CategoriesActivity, VideoActivity::class.java)
                    intent.putExtra("categoryTitle", categoryName)
                    intent.putStringArrayListExtra("videoUrls", arrayList)
                    startActivity(intent)
                }
            })
            categoryRecyclerView.adapter = categoryAdapter
        }
    }
}





