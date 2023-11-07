package com.example.optifit.adapter
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.R
import org.json.JSONArray
import org.json.JSONObject

class FavoriteAdapter(context: Context,  private val dataset: JSONArray) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val webView: WebView = view.findViewById(R.id.favoriteWebView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_videos, parent, false)
        return FavoriteViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.length()
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val url = dataset.get(position).toString()

        holder.webView.settings.javaScriptEnabled = true
        holder.webView.settings.domStorageEnabled = true
        holder.webView.settings.mediaPlaybackRequiresUserGesture = false
        holder.webView.settings.allowContentAccess = true
        holder.webView.settings.allowFileAccess = true

        holder.webView.webViewClient = WebViewClient()
        val htmlContent = buildHtmlContent(url)
        holder.webView.loadData(htmlContent, "text/html", "utf-8")
    }

    private fun buildHtmlContent(url: String): String {
        val htmlBuilder = StringBuilder()
        htmlBuilder.append("<html><body>")
        htmlBuilder.append("<iframe autoplay=\"false\" width=\"100%\" frameBorder=\"0\" src=\"$url\"></iframe>")
        htmlBuilder.append("</body></html>")
        return htmlBuilder.toString()
    }
}

