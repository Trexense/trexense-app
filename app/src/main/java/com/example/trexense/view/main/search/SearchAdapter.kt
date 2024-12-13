package com.example.trexense.view.main.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trexense.data.response.DataSearch
import com.example.trexense.databinding.ItemSearchBinding
import com.example.trexense.view.main.home.DetailHotel

class SearchAdapter(private val onItemClick: (DataSearch) -> Unit) :
    ListAdapter<DataSearch, SearchAdapter.SearchViewHolder>(DIFF_CALLBACK) {

    inner class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataSearch) {
            with(binding) {
                tvName.text = item.name
                root.setOnClickListener {
                    onItemClick(item)
                    val intent = Intent(root.context, DetailHotel::class.java)
                    intent.putExtra(DetailHotel.DATA_HOTEL, item.hotelId)
                    intent.putExtra("HOTEL_ID_SAVE", item.id)
                    root.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding =
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataSearch>() {
            override fun areItemsTheSame(oldItem: DataSearch, newItem: DataSearch): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataSearch, newItem: DataSearch): Boolean {
                return oldItem == newItem
            }
        }
    }
}
