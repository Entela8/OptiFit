package com.example.optifit

import android.content.Context
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.InputStreamContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.security.GeneralSecurityException
import java.util.Arrays

object CategoryVideos {
    private val CLIENT_SECRETS = R.raw.client_secret
    private val SCOPES = Arrays.asList("https://www.googleapis.com/auth/youtube.force-ssl")
    private const val APPLICATION_NAME = "OptiFit"
    private val JSON_FACTORY: JsonFactory = GsonFactory()

    @Throws(IOException::class)
    fun authorize(httpTransport: NetHttpTransport, context: Context?): Credential {
        // Load client secrets from resources
        val clientSecretsStream = context?.resources?.openRawResource(CLIENT_SECRETS)
        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(clientSecretsStream))

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES).build()
        val credential = AuthorizationCodeInstalledApp(flow, LocalServerReceiver()).authorize("user")
        return credential
    }

    @Throws(GeneralSecurityException::class, IOException::class)
    fun getService(context: Context?): YouTube {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val credential = authorize(httpTransport, context)

        return YouTube.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build()
    }

    @Throws(GeneralSecurityException::class, IOException::class, GoogleJsonResponseException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val context: Context? = null // Replace with your actual Android context if available
        val youtubeService = CategoryVideos.getService(context)

        // TODO: For this request to work, you must replace "YOUR_FILE"
        // with a pointer to the actual file you are uploading.
        // The maximum file size for this operation is 2097152.
        val mediaFile = File("YOUR_FILE")
        val mediaContent = InputStreamContent("application/octet-stream", BufferedInputStream(FileInputStream(mediaFile)))
        mediaContent.length = mediaFile.length()

        // Define and execute the API request
        val request = youtubeService.thumbnails()
            .set("https://youtu.be/BdhqubW1GJE?feature=shared", mediaContent)
        val response = request.setPrettyPrint(true).execute()
        println(response)
    }
}
