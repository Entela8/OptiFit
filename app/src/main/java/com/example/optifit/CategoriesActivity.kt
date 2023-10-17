package com.example.optifit

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.activity.ComponentActivity


class CategoriesActivity : ComponentActivity() {


    private var webView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories)

        //BACK BUTTON
        val backArrow = findViewById<ImageView>(R.id.backArrow)

        // Set an OnClickListener for the ImageView
        backArrow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // Handle the back button click event here
                finish() // Finish the current activity to go back
            }
        })

       /* val videoView = findViewById<VideoView>(R.id.videoView)
        videoView.setVideoPath("https://www.youtube.com/watch?v=cTA-TvezdrU&t")
        videoView.start()*/

        webView = findViewById<View>(R.id.webview) as WebView
        webView!!.settings.javaScriptEnabled = true // Activer JavaScript si nécessaire

        webView!!.webViewClient =
            WebViewClient() // Pour ouvrir les URL dans la WebView plutôt que dans le navigateur externe


        val iframe =
            "<iframe src=\"https://www.youtube.com/embed/cTA-TvezdrU?si=vZcR358cRDsKr1kn\" width=\"100%\" height=\"100%\" style=\"border:none;\"></iframe>"
        val mime = "text/html"
        val encoding = "utf-8"

        webView!!.loadData(iframe, mime, encoding)
    }
}