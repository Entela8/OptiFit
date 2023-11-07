package com.example.optifit

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.adapter.CategoryAdapter
import com.example.optifit.adapter.FavoriteAdapter
import com.example.optifit.storage.ApiService
import com.example.optifit.storage.FavoritesService
import com.example.optifit.ui.theme.OptiFitTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {

    private lateinit var searchView: SearchView
    private val listView by lazy { findViewById<ListView>(R.id.idLVWorkouts) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
        val OptiFit = findViewById<TextView>(R.id.optifitText)

        val spannableString = SpannableString(text)

        // Create ForegroundColorSpan for different parts of the text
        val colorSpan1 = ForegroundColorSpan(Color.RED)
        val colorSpan2 = ForegroundColorSpan(Color.GREEN)
        val colorSpan3 = ForegroundColorSpan(Color.BLUE)

        // Apply the color spans to the desired text range
        spannableString.setSpan(colorSpan1, 0, 4, 0)  // Make "Multi" red
        spannableString.setSpan(colorSpan2, 5, 10, 0) // Make "Color" green
        spannableString.setSpan(colorSpan3, 11, 15, 0) // Make "Text" blue

        // Set the SpannableString in the TextView
        textView.text = spannableString
*/
        searchView = findViewById(R.id.searchWorkouts)

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
        refreshFavorites()

        //CATEGORIES RECYCLER VIEW
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.categoriesRecyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        categoryRecyclerView.layoutManager = layoutManager

        val apiService = ApiService(this)
        apiService.get { result ->
            val responseObj : JSONObject = result
            Log.d("JSON", responseObj.toString())

            val categoryAdapter = CategoryAdapter(responseObj)
            categoryAdapter.setOnItemClickListener(object : CategoryAdapter.OnItemClickListener {
                override fun onItemClick(categoryName: String, category: JSONObject)
                {
                    val videoUrlsArray = category.getJSONArray("videoUrls")
                    val arrayList: ArrayList<String> = ArrayList()

                    for (i in 0 until videoUrlsArray.length()) {
                        val videoUrl = videoUrlsArray.getString(i)
                        arrayList.add(videoUrl)
                    }

                    val intent = Intent(this@MainActivity, VideoActivity::class.java).apply {
                        putExtra("categoryTitle", categoryName)
                        putStringArrayListExtra("videoUrls", arrayList)
                    }
                    startActivity(intent)
                }
            })
            categoryRecyclerView.adapter = categoryAdapter
        }

        //FAVORIS TRANSITION
        val favoriteVideos = findViewById<TextView>(R.id.Favoris)

        favoriteVideos.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        //CATEGORY TRANSITION
        val categoriesTitle = findViewById<TextView>(R.id.categoriesTitle)

        categoriesTitle.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        }
    }

    fun refreshFavorites(){
        val favoritesService = FavoritesService(this)
        val favoritesJsonObject = favoritesService.loadFavorites()
        val favoriteUrls = favoritesJsonObject.optJSONArray("urls")

        val noFavorites = findViewById<TextView>(R.id.noFavoritesTextView)

        noFavorites.isVisible =
            !favoritesJsonObject.has("urls") || favoritesJsonObject.getJSONArray("urls").length()==0

        val favoritesRecyclerView = findViewById<RecyclerView>(R.id.favoritesRecyclerView)
        val favoriteLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        favoritesRecyclerView.layoutManager = favoriteLayoutManager

        val favoriteAdapter = favoriteUrls?.let { FavoriteAdapter(this, it) }
        favoritesRecyclerView.adapter = favoriteAdapter
    }

    override fun onResume() {
        super.onResume()
        refreshFavorites()
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