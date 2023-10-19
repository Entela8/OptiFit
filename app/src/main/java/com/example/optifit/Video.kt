package com.example.optifit

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity

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
        val categoryTitleTextView = findViewById<TextView>(R.id.item_text)
        categoryTitleTextView.text = categoryTitle

        // Create HTML content to embed video links into the WebView
        val htmlContent = buildHtmlContent(videoUrls)

        // Initialize the WebView
        webView = findViewById(R.id.videoWebView)
        webView?.settings?.javaScriptEnabled = true
        webView?.webViewClient = WebViewClient()

        // Load the HTML content into the WebView
        webView?.loadData(htmlContent, "text/html", "utf-8")
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

