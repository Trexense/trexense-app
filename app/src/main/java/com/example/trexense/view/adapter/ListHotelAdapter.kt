package com.example.trexense.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.trexense.R
import com.example.trexense.data.response.DataHotel
import com.example.trexense.databinding.ItemHotelBinding
import com.example.trexense.view.createPlan.CreatePlanHotel
import com.example.trexense.view.main.home.DetailHotel
import java.text.NumberFormat
import java.util.Locale

class ListHotelAdapter : ListAdapter<DataHotel, ListHotelAdapter.ViewHolder>(ListHotelAdapter.DiffCallback()) {
    class DiffCallback : DiffUtil.ItemCallback<DataHotel>(){
        override fun areItemsTheSame(oldItem: DataHotel, newItem: DataHotel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataHotel, newItem: DataHotel): Boolean {
            return oldItem == newItem
        }

    }
    class ViewHolder(val binding: ItemHotelBinding): RecyclerView.ViewHolder(binding.root){

        fun bindData(item: DataHotel){
            Glide.with(itemView)
                .load(item.imageUrl)
                .centerCrop()
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.ivHotel)
//            item.image?.let { binding.ivHotel.setImageResource(it) }
            val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            binding.tvTitleHotel.text = item.name
            binding.tvPrice.text = formatter.format(item.cost)
            binding.tvPlace.text = item.address
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailHotel::class.java)
                intent.putExtra(DetailHotel.DATA_HOTEL, item.hotelId)
                itemView.context.startActivity(intent)
            }

            binding.btnCreatePlan.setOnClickListener {
                itemView.context.startActivity(Intent(itemView.context, CreatePlanHotel::class.java))
            }
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