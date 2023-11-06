package com.example.optifit

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.net.toUri
import com.example.optifit.models.Profile
import com.example.optifit.storage.FavoritesJsonFileStorage
import com.example.optifit.storage.FavoritesService
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

class VideoActivity : ComponentActivity() {

    private val videoWebViewList: ArrayList<WebView> = ArrayList()
    private val favoriteButtonList: ArrayList<Button> = ArrayList()

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

        // Initialize the WebView and favorite buttons
        if (videoUrls != null) {
            for (i in 0 until videoUrls.size) {
                addVideoViewAndFavoriteButton(videoUrls[i], i)
            }
        }
    }

    private fun addVideoViewAndFavoriteButton(videoUrl: String, index: Int) {
        val webViewLayout = findViewById<LinearLayout>(R.id.webViewLayout)

        // Create WebView
        val webView = WebView(this)
        webView.id = View.generateViewId()
        val webViewParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        webView.layoutParams = webViewParams
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.settings.allowContentAccess = true
        webView.settings.allowFileAccess = true
        webView.loadUrl(videoUrl)
        webViewLayout.addView(webView)
        videoWebViewList.add(webView)

        // Create Favorite Button
        val favoriteButton = Button(this)
        favoriteButton.id = View.generateViewId()
        val buttonSizeInPixels = 40
        favoriteButton.layoutParams = LinearLayout.LayoutParams(buttonSizeInPixels, buttonSizeInPixels)
        favoriteButton.setBackgroundResource(R.drawable.ic_favorites)
        favoriteButtonList.add(favoriteButton)

        favoriteButton.setOnClickListener {
            addToFavorites(videoUrl)
        }

        webViewLayout.addView(favoriteButton)
    }

    private fun addToFavorites(videoUrl: String) {

       /* val favoritesStorage = FavoritesJsonFileStorage(this)
        val id = favoritesStorage.addFavorite(videoUrl).toString()*/
        FavoritesService(this).saveFavorite(videoUrl)

        Toast.makeText(this, "Video ajoutée aux favoris!", Toast.LENGTH_SHORT).show()

    }

}
