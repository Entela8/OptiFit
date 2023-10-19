package com.example.optifit

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.activity.ComponentActivity

class Video : ComponentActivity() {


    private var webView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_videos)

        //BACK BUTTON
        val backArrow = findViewById<ImageView>(R.id.backArrow)
        // Set an OnClickListener for the ImageView
        backArrow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                finish()
            }
        })


        webView = findViewById<View>(R.id.webview) as WebView
        webView!!.settings.javaScriptEnabled = true

        webView!!.webViewClient =
            WebViewClient() // Pour ouvrir les URL dans la WebView plutôt que dans le navigateur externe


        val iframe =
            "<iframe src=\"https://www.youtube.com/embed/cTA-TvezdrU?si=vZcR358cRDsKr1kn\" width=\"100%\" height=\"100%\" style=\"border:none;\"></iframe>"
        val mime = "text/html"
        val encoding = "utf-8"

        webView!!.loadData(iframe, mime, encoding)
    }
}

