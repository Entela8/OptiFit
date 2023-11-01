package com.example.optifit.adapter
import com.example.optifit.models.Category
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.optifit.R

class CategoryAdapter(private val context: Context, private val dataset: List<Category>) :
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
        Log.d("CategoryAdapter", "Dataset size: ${dataset.size}")
        return dataset.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = context.resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.imageResId)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(category: Category)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
}
