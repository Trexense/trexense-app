package com.example.trexense.view.planDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trexense.data.response.PlanDetailsItem
import com.example.trexense.databinding.ItemDayBinding

class PlanDayAdapter(
    private val onItemClick: (PlanDetailsItem) -> Unit
) : ListAdapter<PlanDetailsItem, PlanDayAdapter.PlanDayViewHolder>(DIFF_CALLBACK) {

    inner class PlanDayViewHolder(private val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlanDetailsItem) {
            with(binding) {
                // Menampilkan title berdasarkan hari
                dayTitle.text = "Day ${item.day}: ${item.date}"

                // Klik pada root layout
                root.setOnClickListener {
                    onItemClick(item)
                }

                // Setup RecyclerView untuk aktivitas harian
                val activityAdapter = ActivityAdapter()
                rcActivityDay.apply {
                    adapter = activityAdapter
                    layoutManager = LinearLayoutManager(itemView.context)

                    setHasFixedSize(true)
                }
                val hotelAdapter = HotelAdapter()
                rcHotel.apply {
                    adapter = hotelAdapter
                    layoutManager = LinearLayoutManager(itemView.context)
                    setHasFixedSize(true)
                }

                // Masukkan daftar aktivitas ke dalam adapter
                activityAdapter.submitList(item.activities)
                hotelAdapter.submitList(item.hotel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanDayViewHolder {
        val binding = ItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanDayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanDayViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlanDetailsItem>() {
            override fun areItemsTheSame(oldItem: PlanDetailsItem, newItem: PlanDetailsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PlanDetailsItem, newItem: PlanDetailsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
