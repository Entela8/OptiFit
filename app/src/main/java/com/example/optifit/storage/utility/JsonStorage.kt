package com.example.optifit.storage.utility


import android.content.Context
import android.util.Log
import org.json.JSONObject

abstract class JsonStorage<T>(
    protected val context: Context
) : IStorage<T> {
    protected abstract fun objectToJson(id: Int, obj: T): JSONObject
    protected abstract fun jsonToObject(json: JSONObject): T

    protected val data = HashMap<Int, T>()
    protected var nextId = -1
    abstract fun getData(callback : (JsonStorage<T>) -> Unit)
    protected abstract fun dataUpdate()

    override fun size(): Int {
        return data.size
    }
    override fun insert(obj: T): Int {
        data[nextId] = obj
        Log.d("ADDED", obj.toString())
        dataUpdate()
        return nextId++
    }
    override fun find(id: Int): T {
        return data.getValue(id)
    }
    override fun find(ids : List<Int>) : List<T> {
        return data.filterKeys {
                id -> ids.contains(id)
        }.values.toList()
    }
    override fun findAll(): List<T> {
        return data.values.toList()
    }
    override fun delete(id: Int) {
        data.remove(id)
        dataUpdate()
    }
    override fun update(id: Int, obj: T) {
        data[id] = obj
        dataUpdate()
    }
}