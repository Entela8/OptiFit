package com.example.optifit.storage

import android.content.Context
import com.example.optifit.models.Profile

import com.example.optifit.storage.utility.file.JSONFileStorage
import org.json.JSONObject

class ProfileStorage(context: Context, name: String) : JSONFileStorage<Profile>(context, name) {
    override fun create(id: Int, obj: Profile): Profile {
        return Profile(obj.id, obj.photo, obj.name,obj.age,obj.description)
    }

    override fun objectToJson(id: Int, obj: Profile): JSONObject {
        var res = JSONObject()
        res.put(Profile.ID,obj.id)
        res.put(Profile.PHOTO, obj.photo)
        res.put(Profile.NAME, obj.name)
        res.put(Profile.AGE,obj.age)
        res.put(Profile.DESCRIPTION,obj.description)
        return res
    }

    override fun jsonToObject(json: JSONObject): Profile {
        return Profile(
            json.getInt(Profile.ID),
            json.getString(Profile.PHOTO),
            json.getString(Profile.NAME),
            json.getString(Profile.AGE),
            json.getString(Profile.DESCRIPTION)
        )
    }
}