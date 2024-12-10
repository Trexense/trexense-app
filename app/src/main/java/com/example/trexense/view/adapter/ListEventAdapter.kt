package com.example.trexense.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.trexense.R
import com.example.trexense.data.models.EventItem
import com.example.trexense.data.models.ImageItem
import com.example.trexense.data.response.DataItem
import com.example.trexense.databinding.ItemEventBinding
import com.example.trexense.databinding.ItemHotelBinding
import com.example.trexense.databinding.SliderItemBinding
import com.example.trexense.view.adapter.ImageAdapter.DiffCallback
import com.example.trexense.view.main.home.DetailEvent
import java.text.NumberFormat
import java.util.Locale

class ListEventAdapter : ListAdapter<DataItem, ListEventAdapter.ViewHolder>(DiffCallback) {
    inner class ViewHolder(val binding: ItemEventBinding): RecyclerView.ViewHolder(binding.root){

        fun bindData(item: DataItem){
            Glide.with(itemView)
                .load(item.imageUrl)
                .centerCrop()
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.ivHotel)
//            item.image?.let { binding.ivHotel.setImageResource(it) }
            binding.tvTitleHotel.text = item.title
            val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            binding.tvPrice.text = formatter.format(item.price)
            binding.tvPlace.text = item.location

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailEvent::class.java)
                intent.putExtra(DetailEvent.DATA_EVENT, item)
                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it) }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DataItem>(){
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}