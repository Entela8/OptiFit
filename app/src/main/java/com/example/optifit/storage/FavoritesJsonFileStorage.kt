package com.example.optifit.storage

import android.content.Context
import android.util.Log
import com.example.optifit.models.Favorites
import com.example.optifit.storage.utility.JsonFileStorage
import org.json.JSONObject

class FavoritesJsonFileStorage(
    context: Context,
) :
    JsonFileStorage<Favorites>(
        "favorites.json", // Use a different file name if needed
        context,
    ) {

    override fun objectToJson(id: Int, obj: Favorites): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("videoUrl", obj.videoUrl)
        return jsonObject
    }

    override fun jsonToObject(json: JSONObject): Favorites {
        val videoUrl = json.getString("videoUrl")
        return Favorites(videoUrl)
    }

    fun addFavorite(videoUrl: String): Int {
        val id = insert(Favorites(videoUrl))
        dataUpdate()
        Log.d("LES FQVORIS", getFavorites().toString())
        return id
    }

    fun getFavorites(): List<Favorites> {
        return findAll()
    }

    fun removeFavorite(id: Int) {
        delete(id)
        dataUpdate()
    }
}
