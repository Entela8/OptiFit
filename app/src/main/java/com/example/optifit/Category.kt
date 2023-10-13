package com.example.optifit

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


data class Category(
        @StringRes val stringResourceId: Int,
        @DrawableRes val imageResId: Int
)
