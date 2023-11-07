package com.example.optifit

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.adapter.VideoListAdapter
import org.json.JSONArray

class VideoActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_videos)

        // BACK BUTTON
        val backArrow = findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        // Retrieve category title and video URLs from Intent
        val categoryTitle = intent.getStringExtra("categoryTitle")
        val videoUrls = intent.getStringArrayListExtra("videoUrls")

        // Set the category title in a TextView
        val categoryTitleTextView = findViewById<TextView>(R.id.categoryTitleTextView)
        categoryTitleTextView.text = categoryTitle

        val videoUrlsJSONArray = JSONArray()

        if (videoUrls != null)
        {
            for (url in videoUrls) {
                videoUrlsJSONArray.put(url)
            }
        }

        val videosRecyclerView = findViewById<RecyclerView>(R.id.categoryVideoRecyclerView)
        videosRecyclerView.layoutManager = LinearLayoutManager(this)

        val favoriteVideoAdapter = VideoListAdapter(this, videoUrlsJSONArray){

        }
        videosRecyclerView.adapter = favoriteVideoAdapter
    }
}
