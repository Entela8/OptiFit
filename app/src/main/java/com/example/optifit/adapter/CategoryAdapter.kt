package com.example.optifit.adapter
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.R
import org.json.JSONObject

class CategoryAdapter(private val context: Context, private val dataset: JSONObject) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    class CategoryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_text)
        val imageView: ImageView = view.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        Log.d("CategoryAdapter", "Dataset size: ${dataset.length()}")
        return dataset.length()
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryName = dataset.names().getString(position)
        val category = dataset.getJSONObject(categoryName)
        val firstVideoUrl = category.getJSONArray("videoUrls").getString(0)

        holder.textView.text = categoryName
        val context: Context = holder.imageView.getContext()
        val id = context.resources.getIdentifier(categoryName.toLowerCase(), "drawable", context.packageName)
        holder.imageView.setImageResource(id)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(categoryName, category)
        }
    }
    interface OnItemClickListener {
        fun onItemClick(categoryName: String, category: JSONObject)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
}
