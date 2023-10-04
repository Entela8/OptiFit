package com.example.optifit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.Favorites
import com.example.optifit.R

class FavoritesAdapter(private val favoriteVideos: List<Favorites>) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val videoThumbnail: ImageView = itemView.findViewById(R.id.videoThumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_videos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = favoriteVideos[position]

        // Set video thumbnail and other information here
        holder.videoThumbnail.setImageResource(video.thumbnailResId)
    }

    override fun getItemCount(): Int {
        return favoriteVideos.size
    }
}
