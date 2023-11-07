package com.example.optifit

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.adapter.VideoListAdapter
import com.example.optifit.services.FavoritesService

class FavoritesActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorite_videos)

        val backArrow = findViewById<ImageView>(R.id.backArrow)

        backArrow.setOnClickListener { finish() }

        refreshVideos()
    }

    private fun refreshVideos()
    {
        val favorites = FavoritesService(this).loadFavorites()
        val videosRecyclerView = findViewById<RecyclerView>(R.id.favoriteVideosRecyclerView)

        val noFavorites = findViewById<TextView>(R.id.noFavorites)

        noFavorites.isVisible =
            !favorites.has("urls") || favorites.getJSONArray("urls").length()==0

        val layoutManager = GridLayoutManager(this, 1)
        videosRecyclerView.layoutManager = layoutManager

        if (favorites.has("urls"))
        {
            val favoriteVideoAdapter = VideoListAdapter(this, favorites.getJSONArray("urls")) {
                refreshVideos()
            }
            videosRecyclerView.adapter = favoriteVideoAdapter
        }
    }
}
