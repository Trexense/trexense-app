package com.example.trexense.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trexense.data.models.HotelItem
import com.example.trexense.data.models.ImageItem
import com.example.trexense.databinding.ItemHotelBinding
import com.example.trexense.databinding.SliderItemBinding
import com.example.trexense.view.adapter.ImageAdapter.DiffCallback

class ListHotelAdapter : ListAdapter<HotelItem, ListHotelAdapter.ViewHolder>(ListHotelAdapter.DiffCallback()) {
    class DiffCallback : DiffUtil.ItemCallback<HotelItem>(){
        override fun areItemsTheSame(oldItem: HotelItem, newItem: HotelItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HotelItem, newItem: HotelItem): Boolean {
            return oldItem == newItem
        }

    }
    class ViewHolder(val binding: ItemHotelBinding): RecyclerView.ViewHolder(binding.root){

        fun bindData(item: HotelItem){
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
        val binding = ItemHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = getItem(position)
        holder.bindData(imageItem)
    }
}