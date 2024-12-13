package com.example.trexense.view.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trexense.databinding.ItemDetailHotelBinding
import com.example.trexense.databinding.ItemPlansHotelBinding

class PlanHotelAdapter(private val plans: List<String>) : RecyclerView.Adapter<PlanHotelAdapter.ViewHolder>(){

    class ViewHolder(val binding: ItemPlansHotelBinding): RecyclerView.ViewHolder(binding.root){

        fun bindData(plan: String){
//            binding.tvItemFacilitesHotel.text = facility
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlansHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = plans.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plan = plans[position]
        holder.binding.namePlan.text = plans[position]
        holder.bindData(plan)
    }
}