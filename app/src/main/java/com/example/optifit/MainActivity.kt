import android.net.Uri
import android.os.Bundle
import android.util.JsonReader
import android.view.View
import android.widget.ArrayAdapter
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
import com.example.optifit.CategoryAdapter
import com.example.optifit.CategoryData
import com.example.optifit.Favorites
import com.example.optifit.R

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
        categoryRecyclerView.adapter = CategoryAdapter(this, myDataset)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.layoutManager = layoutManager

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