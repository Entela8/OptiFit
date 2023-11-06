package com.example.optifit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.activity.ComponentActivity
import com.example.optifit.storage.FavoritesJsonFileStorage
import com.example.optifit.storage.FavoritesService
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class FavoritesActivity : ComponentActivity() {

    private var webView: WebView? = null
    private lateinit var favoritesStorage: FavoritesJsonFileStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorite_videos)

        // BACK BUTTON
        val backArrow = findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        val favorites = FavoritesService(this).loadFavorites()

        if (favorites.has("urls")) {
            // Initialize the WebView
            webView = findViewById(R.id.videoWebViewFavorites)
            webView?.settings?.javaScriptEnabled = true
            webView?.settings?.domStorageEnabled = true
            webView?.settings?.mediaPlaybackRequiresUserGesture = false
            webView?.settings?.allowContentAccess = true
            webView?.settings?.allowFileAccess = true

            webView?.webViewClient = WebViewClient()

            // Build HTML content with embedded video links
            //val htmlContent = buildHtmlContent(favorites.map { it.videoUrl })
            val urlsArray = favorites.getJSONArray("urls")

            // Convert the JSONArray to a List<String>
            val urlsList = jsonArrayToList(urlsArray)

            // Build HTML content with embedded video links
            val htmlContent = buildHtmlContent(urlsList)

            // Load the HTML content into the WebView
            webView?.loadData(htmlContent, "text/html", "utf-8")
        }

        val fav = FavoritesService(this).loadFavorites()
        Log.d("les favs", fav.toString())
    }

    fun jsonArrayToList(jsonArray: JSONArray): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getString(i))
        }
        return list
    }

    // Function to build HTML content with embedded video links
    private fun buildHtmlContent(videoUrls: List<String>): String {
        val htmlBuilder = StringBuilder()
        htmlBuilder.append("<html><body>")
        for (videoUrl in videoUrls) {
            htmlBuilder.append("<iframe width=\"100%\" height=\"300\" src=\"$videoUrl\"></iframe>")
        }
        htmlBuilder.append("</body></html>")
        return htmlBuilder.toString()
    }
}
