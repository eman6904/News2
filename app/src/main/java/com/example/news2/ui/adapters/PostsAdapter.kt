package com.example.news2.ui.adapters

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news2.R
import com.example.news2.data.models.Post
import com.example.news2.databinding.PostModelBinding
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class PostsAdapter(private val list: List<Post>) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: PostModelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val date = binding.date
        val description = binding.description
        val image = binding.image
        val more = binding.more
    }
    private var onClickListener:OnClickListener? = null

    fun formatDateTime(backendDateTime: String): String {

        return try {
            val zonedDateTime = ZonedDateTime.parse(backendDateTime, DateTimeFormatter.ISO_DATE_TIME)

            val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy, hh:mm a",Locale("en"))
            zonedDateTime.format(formatter)
        } catch (e: Exception) {
                "Invalid date"
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PostModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val backendDateTime = list[position].publishedAt
        val formattedDate = backendDateTime?.let { formatDateTime(it) }
        holder.date.text = formattedDate

        holder.title.text = list[position].title ?: "Unavailable title"
        holder.description.text = list[position].description ?:"Unavailable description"

        if(list[position].title=="[Removed]") {
            holder.more.visibility = View.GONE
            Glide.with(holder.binding.root)
                .asBitmap()
                .load(Uri.parse(list[position].urlToImage?:""))
                .error(R.drawable.removed_post_image)
                .placeholder(R.drawable.image)
                .into(holder.image)
        }else{

            if(list[position].urlToImage!=null) {
                Glide.with(holder.binding.root)
                    .asBitmap()
                    .load(Uri.parse(list[position].urlToImage))
                    .error(R.drawable.removed_post_image)
                    .placeholder(R.drawable.image)
                    .into(holder.image)
            }else{
                holder.image.visibility=View.GONE
            }
        }

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, list[position] )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun setOnClickListener(onClickListener:OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {

        fun onClick(position: Int, model:Post)
    }
}
