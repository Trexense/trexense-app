package com.example.trexense.view.main.plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trexense.data.response.DataPlans
import com.example.trexense.databinding.ItemPlanBinding

class PlanHotelAdapter(
    private val plans: List<DataPlans>,
    private val onPlanSelected: (DataPlans) -> Unit
) : RecyclerView.Adapter<PlanHotelAdapter.PlanViewHolder>() {

    inner class PlanViewHolder(private val binding: ItemPlanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plan: DataPlans) {
            binding.planName.text = plan.name
            binding.root.setOnClickListener {
                onPlanSelected(plan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val binding = ItemPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        holder.bind(plans[position])
    }

    override fun getItemCount(): Int = plans.size
}
