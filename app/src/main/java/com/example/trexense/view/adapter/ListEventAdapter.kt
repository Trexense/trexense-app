package com.example.trexense.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trexense.data.models.EventItem
import com.example.trexense.data.models.ImageItem
import com.example.trexense.databinding.ItemEventBinding
import com.example.trexense.databinding.ItemHotelBinding
import com.example.trexense.databinding.SliderItemBinding
import com.example.trexense.view.adapter.ImageAdapter.DiffCallback

class ListEventAdapter : ListAdapter<EventItem, ListEventAdapter.ViewHolder>(ListEventAdapter.DiffCallback()) {
    class DiffCallback : DiffUtil.ItemCallback<EventItem>(){
        override fun areItemsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
            return oldItem == newItem
        }

    }
    class ViewHolder(val binding: ItemEventBinding): RecyclerView.ViewHolder(binding.root){

        fun bindData(item: EventItem){
//            Glide.with(itemView)
//                .load(item.image)
//                .into(binding.ivHotel)
            item.image?.let { binding.ivHotel.setImageResource(it) }
            binding.tvTitleHotel.text = item.name
            binding.tvPrice.text = item.price
            binding.tvPlace.text = item.place
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = getItem(position)
        holder.bindData(imageItem)
    }
}