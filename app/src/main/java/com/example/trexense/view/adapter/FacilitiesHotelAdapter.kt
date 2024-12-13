package com.example.trexense.view.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trexense.databinding.ItemDetailHotelBinding

class FacilitiesHotelAdapter(private val facilities: List<String>) : RecyclerView.Adapter<FacilitiesHotelAdapter.ViewHolder>(){

    class ViewHolder(val binding: ItemDetailHotelBinding): RecyclerView.ViewHolder(binding.root){

        fun bindData(facility: String){
            binding.tvItemFacilitesHotel.text = facility
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetailHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = facilities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val facility = facilities[position]
        holder.bindData(facility)
    }
}