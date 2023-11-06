package com.example.optifit.storage

import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.android.volley.Request
import android.content.Context
import android.util.Log

class ApiService (context: Context){

    val context : Context
    private var response = JSONObject()
    private var URL = "http://51.68.91.213/gr-3-8/categories.json"
    init {
        this.context = context
    }
    fun get(myCallback: (result: JSONObject) -> Unit)
    {
        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET, URL,
            { Sresponse ->
                response = JSONObject(Sresponse)
                myCallback.invoke(response)
            },
            {
                Log.e("SERVER","get request on " + URL + " didnt work")
            })
        queue.add(stringRequest)
    }

    fun getJSON(): JSONObject
    {
        return this.response
    }

}