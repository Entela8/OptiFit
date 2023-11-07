package com.example.optifit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.adapter.CategoryAdapter
import com.example.optifit.adapter.VideoListAdapter
import com.example.optifit.storage.FavoritesService
import org.json.JSONArray

class FavoritesActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorite_videos)

        val backArrow = findViewById<ImageView>(R.id.backArrow)

        backArrow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        val favorites = FavoritesService(this).loadFavorites()
        val videosRecyclerView = findViewById<RecyclerView>(R.id.favoriteVideosRecyclerView)
        val layoutManager = GridLayoutManager(this, 1)
        videosRecyclerView.layoutManager = layoutManager

        if (favorites.has("urls"))
        {
            Log.d("ON PASSE", "la dedans")
            val favoriteVideoAdapter = VideoListAdapter(this, favorites.getJSONArray("urls"))
            videosRecyclerView.adapter = favoriteVideoAdapter
        }
    }

    fun jsonArrayToList(jsonArray: JSONArray): List<String>
    {
        val list = mutableListOf<String>()

        for (i in 0 until jsonArray.length())
        {
            list.add(jsonArray.getString(i))
        }
        return list
    }
}
