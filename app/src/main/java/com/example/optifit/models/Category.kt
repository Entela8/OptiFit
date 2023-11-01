package com.example.optifit.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


data class Category(
        @StringRes val stringResourceId: Int,
        @DrawableRes val imageResId: Int,
        val categoryTitle: String,
        var videoUrls: List<String>? = null
)
