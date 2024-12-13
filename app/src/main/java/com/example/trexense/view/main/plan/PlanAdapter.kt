package com.example.trexense.view.main.plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trexense.data.response.DataPlans
import com.example.trexense.databinding.ItemPlansBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class PlanAdapter(private val onItemClick: (DataPlans) -> Unit) :
    ListAdapter<DataPlans, PlanAdapter.PlanViewHolder>(DIFF_CALLBACK) {


    inner class PlanViewHolder(private val binding: ItemPlansBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataPlans) {
            with(binding) {
                // Format input dan output
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                // Parsing dan formatting startDate
                val startDate = item.startDate?.let {
                    inputFormat.parse(it)?.let { date ->
                        outputFormat.format(date)
                    }
                } ?: "N/A"

                // Parsing dan formatting endDate
                val endDate = item.endDate?.let {
                    inputFormat.parse(it)?.let { date ->
                        outputFormat.format(date)
                    }
                } ?: "N/A"

                // Set data ke TextView
                namaEvent.text = item.name
                tglEvent.text = "Start: $startDate - End: $endDate"

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
