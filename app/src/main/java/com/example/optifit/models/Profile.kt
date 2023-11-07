package com.example.optifit.models

data class Profile(
    val id: Int,
    val photo: String,
    val name: String,
    val age: String,
    val description: String
)
{
    companion object{
        const val ID = "id"
        const val PHOTO = "photo"
        const val NAME = "name"
        const val AGE = "age"
        const val DESCRIPTION = "description"
    }
}