package com.example.optifit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.adapter.FavoritesAdapter
import com.example.optifit.ui.theme.OptiFitTheme

class MainActivity : ComponentActivity() {

    lateinit var workouts: ListView
    lateinit var listAdapter: ArrayAdapter<String>
    lateinit var workoutsList: ArrayList<String>
    lateinit var searchView: SearchView
    val listView by lazy { findViewById<ListView>(R.id.idLVWorkouts) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workouts = findViewById(R.id.idLVWorkouts)
        searchView = findViewById(R.id.searchWorkouts)

        workoutsList = ArrayList()
        workoutsList.add("Abdos")
        workoutsList.add("Biceps")
        workoutsList.add("Pectoraux")
        workoutsList.add("Dos")
        workoutsList.add("Jambes")
        workoutsList.add("Fessiers")
        workoutsList.add("Épaules")
        workoutsList.add("Triceps")

        workoutsList.add("Calisthénie")
        workoutsList.add("Musculation")
        workoutsList.add("Yoga")
        workoutsList.add("Boxe")
        workoutsList.add("Étirements")
        workoutsList.add("Pilates")
        workoutsList.add("CrossFit")

        listAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            workoutsList
        )

        workouts.adapter = listAdapter

        searchView.setOnSearchClickListener {
            listView.visibility = View.VISIBLE
        }

        searchView.setOnCloseListener {
            listView.visibility = View.GONE
            false
        }

        val favoritesRecyclerView = findViewById<RecyclerView>(R.id.favoritesRecyclerView)

        // Create instances of Favorite videos with titles, descriptions, and thumbnail resource IDs
        val favoriteVideos = mutableListOf<Favorites>(
            Favorites("Video 1", "Description 1", R.drawable.thumbnails),
            Favorites("Video 2", "Description 2", R.drawable.thumbnails),
            // Add more favorite videos here with their respective thumbnail resource IDs
        )

        // Initialize and set the adapter (assuming you have a RecyclerView)
        val adapter = FavoritesAdapter(favoriteVideos)
        favoritesRecyclerView.adapter = adapter


        //CATEGORIES RECYCLER VIEW
        val myDataset = CategoryData().loadCategory()
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.categoriesRecyclerView)
        val layoutManager = GridLayoutManager(this, 3) // Set spanCount to 3 for a grid with 3 items in each row
        categoryRecyclerView.layoutManager = layoutManager
        categoryRecyclerView.adapter = CategoryAdapter(this, myDataset)

        //CATEGORY TRANSITION
        val categoriesTitle = findViewById<TextView>(R.id.categoriesTitle)

        // Set an OnClickListener for the "Categories" title
        categoriesTitle.setOnClickListener {
            // When clicked, navigate to CategoriesActivity
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OptiFitTheme {
        Greeting("Android")
    }
}