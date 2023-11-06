package com.example.optifit.storage.utility

interface IStorage<T> {
    fun insert(obj: T): Int
    fun size(): Int
    fun find(id: Int): T
    fun find(ids : List<Int>) : List<T>
    fun findAll(): List<T>
    fun delete(id: Int)
    fun update(id: Int, obj: T)
}