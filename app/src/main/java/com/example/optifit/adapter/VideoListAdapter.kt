package com.example.optifit.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.R
import com.example.optifit.storage.FavoritesService
import org.json.JSONArray
import org.json.JSONObject

class VideoListAdapter(private val context: Context, private val videoUrls: JSONArray, private val onFavorite : () -> Unit) :
    RecyclerView.Adapter<VideoListAdapter.VideoHolder>() {

    class VideoHolder(private val view: View) : RecyclerView.ViewHolder(view)
    {
        val favoriteButton: ImageButton = view.findViewById(R.id.videoFavoriteButton)
        val videoWebView : WebView = view.findViewById(R.id.videoWebView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder
    {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_line, parent, false)
        return VideoHolder(adapterLayout)
    }
    override fun getItemCount(): Int {
        return videoUrls.length()
    }
    override fun onBindViewHolder(holder: VideoHolder, position: Int)
    {
        val url = videoUrls.get(position).toString()

        holder.videoWebView.settings.javaScriptEnabled = true
        holder.videoWebView.settings.domStorageEnabled = true
        holder.videoWebView.settings.mediaPlaybackRequiresUserGesture = false
        holder.videoWebView.settings.allowContentAccess = true
        holder.videoWebView.settings.allowFileAccess = true

        holder.videoWebView.webViewClient = WebViewClient()
        val htmlContent = buildHtmlContent(url)
        holder.videoWebView.loadData(htmlContent, "text/html", "utf-8")

        val favoriteService = FavoritesService(context)

        holder.favoriteButton.setImageResource(if (favoriteService.isFavorite(url)) R.drawable.heart_full else R.drawable.heart_empty)

        holder.favoriteButton.setOnClickListener {
            if(favoriteService.isFavorite(url))
            {
                favoriteService.deleteFavorite(url)
                holder.favoriteButton.setImageResource(R.drawable.heart_empty)

                onFavorite()
            }
            else
            {
                favoriteService.saveFavorite(url)
                holder.favoriteButton.setImageResource(R.drawable.heart_full)
                onFavorite()
            }
        }
    }
    private fun buildHtmlContent(url: String): String {
        val htmlBuilder = StringBuilder()
        htmlBuilder.append("<html><body>")
        htmlBuilder.append("<iframe width=\"100%\" style=\"aspect-ratio: 16/9\" src=\"$url\"></iframe>")
        htmlBuilder.append("</body></html>")
        return htmlBuilder.toString()
    }
}

