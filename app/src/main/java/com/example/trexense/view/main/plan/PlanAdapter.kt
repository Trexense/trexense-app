package com.example.trexense.view.main.plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trexense.data.response.DataPlans
import com.example.trexense.databinding.ItemPlansBinding

class PlanAdapter(private val onItemClick: (DataPlans) -> Unit) :
    ListAdapter<DataPlans, PlanAdapter.PlanViewHolder>(DIFF_CALLBACK) {

    inner class PlanViewHolder(private val binding: ItemPlansBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataPlans) {
            with(binding) {
                namaEvent.text = item.name
                tglEvent.text = "Start: ${item.startDate} - End: ${item.endDate}"

                root.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val binding =
            ItemPlansBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataPlans>() {
            override fun areItemsTheSame(oldItem: DataPlans, newItem: DataPlans): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataPlans, newItem: DataPlans): Boolean {
                return oldItem == newItem
            }
        }
    }
}
