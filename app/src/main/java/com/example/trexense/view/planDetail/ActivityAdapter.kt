package com.example.trexense.view.planDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trexense.data.response.ActivitiesItem
import com.example.trexense.databinding.ItemActivityBinding

class ActivityAdapter :
    ListAdapter<ActivitiesItem, ActivityAdapter.ActivityViewHolder>(DIFF_CALLBACK) {

    inner class ActivityViewHolder(private val binding: ItemActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ActivitiesItem) {
            with(binding) {
                activityName.text = item.name
                activityLocation.text = "Location: ${item.location}"
                activityCost.text = "Cost: $${item.cost}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding =
            ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ActivitiesItem>() {
            override fun areItemsTheSame(oldItem: ActivitiesItem, newItem: ActivitiesItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ActivitiesItem, newItem: ActivitiesItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
