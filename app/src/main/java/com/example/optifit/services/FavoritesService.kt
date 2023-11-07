package com.example.optifit.storage

import android.content.Context
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

class FavoritesService(private val context: Context){

    private var filename : String = "favorites.json"

    fun loadFavorites() : JSONObject
    {
        val file = File(context.filesDir, filename)

        val res = JSONObject()

        if (file.exists())
        {
            val json = file.readText()

            if (json.isNotEmpty())
            {
                val favorites = JSONObject(json)
                return favorites
            }
        }

        return res
    }

    fun saveFavorite(videoUrl: String)
     {
         val existingFav = this.loadFavorites()
         var favorites: JSONObject

         if (existingFav.length() == 0)
         {
             favorites = JSONObject()
             favorites.put("urls", JSONArray())
         }
         else
         {
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

         try
         {
             saveAsFile(favorites)
             Toast.makeText(context, "Favori sauvegarde avec succes", Toast.LENGTH_SHORT).show()
         }
         catch (e: Exception)
         {
             Toast.makeText(context, "UNE ERREUR EST SURVENUE" + e.message, Toast.LENGTH_SHORT).show()
         }
     }

    fun deleteFavorite(videoUrl: String)
    {
        val favorites = this.loadFavorites()

        if (favorites.has("urls"))
        {
            val urlsArray = favorites.getJSONArray("urls")
            // Recherchez l'URL à supprimer dans le tableau
            for (i in 0 until urlsArray.length())
            {
                if (urlsArray.getString(i) == videoUrl)
                {
                    // Supprimez l'URL du tableau
                    urlsArray.remove(i)
                    break // Sortez de la boucle une fois que vous avez trouvé et supprimé l'URL
                }
            }
            // Mettez à jour l'objet "favorites" avec le tableau "urls" mis à jour
            favorites.put("urls", urlsArray)

            try
            {
                saveAsFile(favorites)
                Toast.makeText(context, "Le favori a été supprimé", Toast.LENGTH_SHORT).show()
            }
            catch (e: Exception)
            {
                Toast.makeText(context, "UNE ERREUR EST SURVENUE" + e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun isFavorite(videoUrl: String): Boolean
    {
        val favorites = this.loadFavorites()

        if(!favorites.has("urls")) return false

        val videoArray = favorites.getJSONArray("urls")

        var isElementExists = false

        for (i in 0 until videoArray.length())
        {
            if (videoArray[i] == videoUrl) {
                isElementExists = true
                break
            }
        }

        return isElementExists
    }

    fun saveAsFile(obj: JSONObject)
    {
        val file = File(context.filesDir, filename)
        val outputStream = FileOutputStream(file)
        outputStream.write(obj.toString().toByteArray())
        outputStream.close()
    }
 }