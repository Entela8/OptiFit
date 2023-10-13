package com.example.optifit

import com.example.optifit.Category

class CategoryData() {

    fun loadCategory(): List<Category> {
        return listOf<Category>(
            Category(R.string.abdos, R.drawable.abdos),
            Category(R.string.biceps, R.drawable.biceps),
            Category(R.string.epaules, R.drawable.epaules),
            Category(R.string.fessiers, R.drawable.fessiers),
            Category(R.string.jambes, R.drawable.jambes),
            Category(R.string.pectoraux, R.drawable.pectoraux),
            Category(R.string.tricpes, R.drawable.triceps),
            Category(R.string.boxe, R.drawable.boxe),
            Category(R.string.calisthenie, R.drawable.calisthenie),
            Category(R.string.crossfit, R.drawable.crossfit),
            Category(R.string.etirements, R.drawable.etirements),
            Category(R.string.musculation, R.drawable.musculation),
            Category(R.string.yoga, R.drawable.yoga),
            Category(R.string.pilates, R.drawable.pilates),
        )
    }

}

