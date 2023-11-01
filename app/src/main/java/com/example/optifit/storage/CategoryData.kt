package com.example.optifit.storage

import com.example.optifit.R
import com.example.optifit.models.Category

class CategoryData() {

    fun loadCategory(): List<Category> {
        return listOf<Category>(
            Category(R.string.abdos, R.drawable.abdos, categoryTitle = "Abdos"),
            Category(R.string.biceps, R.drawable.biceps, categoryTitle = "Biceps"),
            Category(R.string.epaules, R.drawable.epaules, categoryTitle = "Epaules"),
            Category(R.string.fessiers, R.drawable.fessiers, categoryTitle = "Fessiers"),
            Category(R.string.jambes, R.drawable.jambes, categoryTitle = "Jambes"),
            Category(R.string.pectoraux, R.drawable.pectoraux, categoryTitle = "Pectoraux"),
            Category(R.string.tricpes, R.drawable.triceps, categoryTitle = "Triceps"),
            Category(R.string.boxe, R.drawable.boxe, categoryTitle = "Boxe"),
            Category(R.string.calisthenie, R.drawable.calisthenie, categoryTitle = "Calisthenie"),
            Category(R.string.crossfit, R.drawable.crossfit, categoryTitle = "Crossfit"),
            Category(R.string.etirements, R.drawable.etirements, categoryTitle = "Etirements"),
            Category(R.string.musculation, R.drawable.musculation, categoryTitle = "Musculation"),
            Category(R.string.yoga, R.drawable.yoga, categoryTitle = "Yoga"),
            Category(R.string.pilates, R.drawable.pilates, categoryTitle = "Pilates"),
        )
    }

}

