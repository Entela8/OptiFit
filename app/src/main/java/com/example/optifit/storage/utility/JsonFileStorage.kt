package com.example.optifit.storage.utility

import android.content.Context
import android.util.Log
import org.json.JSONArray
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

abstract class JsonFileStorage<T>(
    private val fileName : String,
    context: Context,
)
    : JsonStorage<T>(context) {

    @OptIn(ExperimentalStdlibApi::class)
    override fun getData(callback : (JsonStorage<T>) -> Unit) {
        try {
            val file = File(context.filesDir, fileName)

            if (!file.exists()) {
                file.createNewFile()
                FileOutputStream(file).use {
                    it.write("[]".toByteArray())
                }
            } else {
                FileInputStream(file).bufferedReader().use {

                    val builder = StringBuilder()
                    var temp = it.readLine()
                    while(temp != null) {
                        builder.append(temp)
                        temp = it.readLine()
                    }
                    try {
                        val json = JSONArray(builder.toString())
                        nextId = json.length()
                        for (i in 0..<nextId) {
                            val obj = jsonToObject(json.getJSONObject(i))
                            data[i] = obj
                        }
                    } catch (e: FileNotFoundException) {
                        nextId = 0
                    }
                }
            }
            callback(this)
        } catch (e: FileNotFoundException) {
            Log.d("YA PA DE PANO", e.toString())
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun dataUpdate() {
        try {
            val file = File(context.filesDir, fileName)
            FileOutputStream(file).use {
                val jsonArray = JSONArray()
                for (key in data.keys) {
                    jsonArray.put(objectToJson(key, find(key)))
                }
                it.write(jsonArray.toString().toByteArray())
            }
        } catch (e: FileNotFoundException) {
            Log.d("ERROR JSONFILESTORAGE", e.toString())
        }
    }


}