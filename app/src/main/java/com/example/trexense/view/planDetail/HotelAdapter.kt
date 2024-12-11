package com.example.trexense.view.planDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trexense.data.response.HotelItem
import com.example.trexense.databinding.ItemDayHotelBinding

class HotelAdapter : ListAdapter<HotelItem, HotelAdapter.HotelViewHolder>(DIFF_CALLBACK) {

    inner class HotelViewHolder(private val binding: ItemDayHotelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hotel: HotelItem) {
            with(binding) {
                hotelName.text = hotel.hotelDetail.name
                hotelAddress.text = hotel.hotelDetail.address
                hotelCost.text = hotel.hotelDetail.cost.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val binding = ItemDayHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = getItem(position)
        if (hotel != null) {
            holder.bind(hotel)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HotelItem>() {
            override fun areItemsTheSame(oldItem: HotelItem, newItem: HotelItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HotelItem, newItem: HotelItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
