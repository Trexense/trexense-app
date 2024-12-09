package com.example.trexense.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trexense.data.models.EventItem
import com.example.trexense.data.models.ImageItem
import com.example.trexense.data.response.DataItem
import com.example.trexense.databinding.ItemEventBinding
import com.example.trexense.databinding.ItemHotelBinding
import com.example.trexense.databinding.SliderItemBinding
import com.example.trexense.view.adapter.ImageAdapter.DiffCallback

class ListEventAdapter : PagingDataAdapter<DataItem, ListEventAdapter.ViewHolder>(ListEventAdapter.DiffCallback()) {
    class DiffCallback : DiffUtil.ItemCallback<DataItem>(){
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

    }
    inner class ViewHolder(val binding: ItemEventBinding): RecyclerView.ViewHolder(binding.root){

        fun bindData(item: DataItem){
            Glide.with(itemView)
                .load(item.imageUrl)
                .into(binding.ivHotel)
//            item.image?.let { binding.ivHotel.setImageResource(it) }
            binding.tvTitleHotel.text = item.title
            binding.tvPrice.text = item.price.toString()
            binding.tvPlace.text = item.location
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it) }
    }
}