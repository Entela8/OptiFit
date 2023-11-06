package com.example.optifit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.R
import com.example.optifit.models.Favorites
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoritesAdapter(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getFavoriteVideos(): List<Favorites> {
        val favoritesJson = sharedPreferences.getString("favoriteVideos", "[]")
        return gson.fromJson(favoritesJson, object : TypeToken<List<Favorites>>() {}.type)
    }

    fun addFavoriteVideo(video: Favorites) {
        val currentFavorites = getFavoriteVideos().toMutableList()
        currentFavorites.add(video)
        saveFavorites(currentFavorites)
    }

    fun removeFavoriteVideo(video: Favorites) {
        val currentFavorites = getFavoriteVideos().toMutableList()
        currentFavorites.remove(video)
        saveFavorites(currentFavorites)
    }

    private fun saveFavorites(favorites: List<Favorites>) {
        val favoritesJson = gson.toJson(favorites)
        sharedPreferences.edit().putString("favoriteVideos", favoritesJson).apply()
    }
}
