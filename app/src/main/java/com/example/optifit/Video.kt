package com.example.optifit

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.optifit.R
import com.example.optifit.adapter.FavoritesAdapter
import com.example.optifit.models.Favorites

class Video : ComponentActivity() {

    private var webView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_videos)

        //BACK BUTTON
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

        // Create HTML content to embed video links into the WebView
        val htmlContent = buildHtmlContent(videoUrls)

        // Initialize the WebView
        webView = findViewById(R.id.videoWebView)
        webView?.settings?.javaScriptEnabled = true
        webView?.settings?.domStorageEnabled = true
        webView?.settings?.mediaPlaybackRequiresUserGesture = false
        webView?.settings?.allowContentAccess = true
        webView?.settings?.allowFileAccess = true

        webView?.webViewClient = WebViewClient()

        // Load the HTML content into the WebView
        webView?.loadData(htmlContent, "text/html", "utf-8")

        //Favorite button
        val favoritesManager = FavoritesAdapter(this)
        val favoriteButton = findViewById<Button>(R.id.favoriteButton)

        favoriteButton.setOnClickListener {
            val videoUrl = "https://www.youtube.com/embed/your_video_id" // Get the video URL
            val favoriteVideo = Favorites(videoUrl)
            favoritesManager.addFavoriteVideo(favoriteVideo)
            Toast.makeText(this, "Video ajouté à les favoris!", Toast.LENGTH_SHORT).show()
        }

    }

    // Function to build HTML content with embedded video links
    private fun buildHtmlContent(videoUrls: List<String>?): String {
        val htmlBuilder = StringBuilder()
        htmlBuilder.append("<html><body>")
        if (videoUrls != null) {
            for (videoUrl in videoUrls) {
                htmlBuilder.append("<iframe width=\"100%\" height=\"300\" src=\"$videoUrl\"></iframe>")
            }
        }
        htmlBuilder.append("</body></html>")
        return htmlBuilder.toString()
    }
}

