package com.example.optifit.storage

import android.content.Context
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

class FavoritesService(val context: Context){

    private var filename : String = "favorites.json"

    fun loadFavorites() : JSONObject {
        val file = File(context.filesDir, filename)

        val res = JSONObject()

        if (file.exists()) {
            val json = file.readText()
            if (json.isNotEmpty()) {
                val favorites = JSONObject(json)
                return favorites
            }
        }

        return res
    }

     fun saveFavorite(videoUrl: String) {
         /*favorites
  urls:[
      url1,
      url2
  ]
   */

         val existingFav = this.loadFavorites()
         var favorites: JSONObject

         if (existingFav.length() == 0) {
             favorites = JSONObject()
             favorites.put("urls", JSONArray())
         } else {
             favorites = existingFav
         }

// Récupérer le tableau "urls"
         val urlsArray = favorites.getJSONArray("urls")

// Ajouter un nouvel élément à la fin du tableau
         val newUrl = videoUrl
         urlsArray.put(newUrl)

// Créer un nouvel objet JSON mis à jour
         val updatedJsonObj = JSONObject()
         updatedJsonObj.put("urls", urlsArray)

// Mettre à jour la variable favorites avec l'objet JSON mis à jour
         favorites = updatedJsonObj


         // Save the JSON object to a file
         val file = File(context.filesDir, filename)
         val outputStream = FileOutputStream(file)
         outputStream.write(favorites.toString().toByteArray())
         outputStream.close()
         Toast.makeText(context, "Favori sauvegarde avec succes", Toast.LENGTH_SHORT).show()
     }

 }