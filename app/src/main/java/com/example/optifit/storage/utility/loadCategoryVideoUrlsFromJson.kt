package com.example.optifit.storage.utility

import org.json.JSONObject

fun loadCategoryVideoUrlsFromJson(json: JSONObject, categoryTitle: String): List<String> {
    val categoryJson = json.optJSONObject(categoryTitle)
    val videoUrlsJsonArray = categoryJson?.optJSONArray("videoUrls")

    val videoUrls = mutableListOf<String>()
    videoUrlsJsonArray?.let {
        for (i in 0 until it.length()) {
            videoUrls.add(it.getString(i))
        }
    }

    return videoUrls
}
