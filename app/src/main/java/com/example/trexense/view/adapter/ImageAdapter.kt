package com.example.trexense.view.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trexense.data.models.ImageItem
import com.example.trexense.databinding.SliderItemBinding

class ImageAdapter : ListAdapter<ImageItem,ImageAdapter.ViewHolder>(DiffCallback()){

    class DiffCallback : DiffUtil.ItemCallback<ImageItem>(){
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }

    }
    class ViewHolder(val binding: SliderItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bindData(item: ImageItem){
            Glide.with(itemView)
                .load(item.url)
                .into(binding.imageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = getItem(position)
        holder.bindData(imageItem)
    }
}