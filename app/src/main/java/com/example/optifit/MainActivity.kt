import android.net.Uri
import android.os.Bundle
import android.util.JsonReader
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.adapter.FavoritesAdapter
import com.example.optifit.ui.theme.OptiFItTheme
import parseCategory
import java.io.InputStreamReader
import com.example.optifit.Category
import com.example.optifit.CategoryAdapter
import com.example.optifit.Favorites
import com.example.optifit.R
import java.io.File


class MainActivity : ComponentActivity() {

    lateinit var workouts: ListView
    lateinit var listAdapter: ArrayAdapter<String>
    lateinit var workoutsList: ArrayList<String>
    lateinit var searchView: SearchView
    private val categoryList = mutableListOf<Category>()
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

        val categoryTitle = listOf(
            "abdos.jpg", "biceps.jpg", "pectoraux.jpg", "dos.jpg", "jambes.jpg",
            "fessiers.jpg", "épaules.jpg", "triceps.jpg", "calisthénie.jpg",
            "musculation.jpg", "yoga.jpg", "boxe.jpg", "étirements.jpg", "pilates.jpg", "crossfit.jpg"
        )

        val categoryRecyclerView = findViewById<RecyclerView>(R.id.categoriesRecyclerView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.layoutManager = layoutManager

        // Initialize the com.example.optifit.CategoryAdapter with an empty list (you'll update it later)
        val categoryAdapter = CategoryAdapter(mutableListOf())
        categoryRecyclerView.adapter = categoryAdapter


        val imgFile = File("/res/drawable/abdos.jpg")
        if (imgFile.exists()) {
            val myImage = ImageView(this)
            myImage.setImageURI(Uri.fromFile(imgFile))
        }


        /*
        try {
            val inputStream = resources.openRawResource(R.raw.categories)
            val jsonReader = JsonReader(InputStreamReader(inputStream, "UTF-8"))

            jsonReader.beginArray()
            while (jsonReader.hasNext()) {
                val category = parseCategory(jsonReader)
                categoryList.add(com.example.optifit.Category(category.title, category.imageResId, category.videoUrls))
            }
            jsonReader.endArray()

            // Create a new adapter with the updated data and set it to the RecyclerView
            val categoryAdapter = CategoryAdapter(categoryList)
            categoryRecyclerView.adapter = categoryAdapter

        } catch (e: Exception) {
            e.printStackTrace()
        } */


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (workoutsList.contains(query)) {
                    listAdapter.filter.filter(query)
                } else {
                    Toast.makeText(this@MainActivity, "No Language found..", Toast.LENGTH_LONG)
                        .show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listAdapter.filter.filter(newText)
                return false
            }
        })
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
    OptiFItTheme {
        Greeting("Android")
    }
}