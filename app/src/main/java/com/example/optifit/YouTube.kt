package com.example.optifit

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.CategoryVideos
import com.example.optifit.R
import com.google.api.services.youtube.model.SearchListResponse
import com.google.api.services.youtube.model.SearchResult
import com.squareup.picasso.Picasso


class YouTube : AppCompatActivity() {
    private val youtubeService = CategoryVideos.getService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories)

        // Call a function to fetch video data (e.g., search for videos)
        val searchResults = searchForVideos()

        // Display the video thumbnails and titles
        displayVideoResults(searchResults)
    }

    private fun searchForVideos(): List<SearchResult> {
        val query = "Your Search Query" // Replace with your search query
        val search = youtubeService.search().list("id,snippet")
        search.q = query
        val response: SearchListResponse = search.execute()
        return response.items
    }

    private fun displayVideoResults(searchResults: List<SearchResult>) {
        val resultContainer = findViewById<RecyclerView>(R.id.categoriesRecyclerView)

        for (result in searchResults) {
            val videoId = result.id.videoId
            val title = result.snippet.title
            val thumbnailUrl = result.snippet.thumbnails.default.url

            val thumbnailImageView = ImageView(this)
            Picasso.get().load(thumbnailUrl).into(thumbnailImageView)

            val titleTextView = TextView(this)
            titleTextView.text = title

            resultContainer.addView(thumbnailImageView)
            resultContainer.addView(titleTextView)
        }
    }
}
