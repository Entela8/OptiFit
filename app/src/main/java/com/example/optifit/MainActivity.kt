package com.example.optifit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
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
import com.example.optifit.models.Favorites
import com.example.optifit.storage.utility.ApiService
import com.example.optifit.ui.theme.OptiFitTheme
import org.json.JSONObject

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

        //PROFILE TRANSITION
        val profile = findViewById<ImageView>(R.id.profilePicture)
        profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        //FAVORITES
        val favoritesRecyclerView = findViewById<RecyclerView>(R.id.favoritesRecyclerView)
        val favoriteVideos = mutableListOf<Favorites>(
            Favorites("Video 1", "Description 1", R.drawable.thumbnails),
            Favorites("Video 2", "Description 2", R.drawable.thumbnails),
        )
        val adapter = FavoritesAdapter(favoriteVideos)
        favoritesRecyclerView.adapter = adapter

        //CATEGORIES RECYCLER VIEW
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.categoriesRecyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        categoryRecyclerView.layoutManager = layoutManager

        val apiService = ApiService(this);
        apiService.get() { result ->
            val responseObj : JSONObject = result
            Log.d("JSON", responseObj.toString())

            val categoryAdapter = CategoryAdapter(this, responseObj)
            categoryAdapter.setOnItemClickListener(object : CategoryAdapter.OnItemClickListener {
                override fun onItemClick(categoryName: String, category: JSONObject)
                {
                    val videoUrlsArray = category.getJSONArray("videoUrls")
                    val arrayList: ArrayList<String> = ArrayList()

                    for (i in 0 until videoUrlsArray.length()) {
                        val videoUrl = videoUrlsArray.getString(i)
                        arrayList.add(videoUrl)
                    }

                    val intent = Intent(this@MainActivity, Video::class.java).apply {
                        putExtra("categoryTitle", categoryName)
                        putStringArrayListExtra("videoUrls", arrayList)
                    }
                    startActivity(intent)
                }
            })
            categoryRecyclerView.adapter = categoryAdapter
        }

        //CATEGORY TRANSITION
        val categoriesTitle = findViewById<TextView>(R.id.categoriesTitle)

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