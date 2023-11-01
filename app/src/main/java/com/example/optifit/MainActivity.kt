package com.example.optifit

import android.content.Intent
import android.os.Bundle
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
import com.example.optifit.adapter.CategoryAdapter
import com.example.optifit.adapter.FavoritesAdapter
import com.example.optifit.models.Category
import com.example.optifit.models.Favorites
import com.example.optifit.storage.CategoryData
import com.example.optifit.storage.utility.loadCategoryVideoUrlsFromJson
import com.example.optifit.ui.theme.OptiFitTheme
import org.json.JSONObject
import java.io.IOException

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
        val favoriteVideos = mutableListOf<Favorites>(
            Favorites("Video 1", "Description 1", R.drawable.thumbnails),
            Favorites("Video 2", "Description 2", R.drawable.thumbnails),
        )
        val adapter = FavoritesAdapter(favoriteVideos)
        favoritesRecyclerView.adapter = adapter


        //CATEGORIES RECYCLER VIEW
        // Get the category data and set up the categoryRecyclerView
        // Load category data
        val myDataset = CategoryData().loadCategory()
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.categoriesRecyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        categoryRecyclerView.layoutManager = layoutManager
        // Load and parse the JSON data from your source
        val jsonString = try {
            val inputStream = resources.openRawResource(R.raw.categories)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }

        val json = JSONObject(jsonString)

        // Update the videoUrls for each category from the JSON data
        for (category in myDataset) {
            category.videoUrls = loadCategoryVideoUrlsFromJson(json, category.categoryTitle)
        }

        // Create and set the CategoryAdapter with item click listener
        val categoryAdapter = CategoryAdapter(this, myDataset)
        categoryAdapter.setOnItemClickListener(object : CategoryAdapter.OnItemClickListener {
            override fun onItemClick(category: Category) {
                // Handle the item click, e.g., open CategoryDetailsActivity
                val intent = Intent(this@MainActivity, Video::class.java).apply {
                    putExtra("categoryTitle", category.categoryTitle)
                    putStringArrayListExtra("videoUrls", ArrayList(category.videoUrls ?: emptyList()))
                }
                startActivity(intent)
            }
        })

        categoryRecyclerView.adapter = categoryAdapter


        //CATEGORY TRANSITION
        val categoriesTitle = findViewById<TextView>(R.id.categoriesTitle)

        // OnClickListener  "Categories" title
        categoriesTitle.setOnClickListener {
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