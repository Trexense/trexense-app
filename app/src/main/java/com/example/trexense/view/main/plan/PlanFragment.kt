package com.example.trexense.view.main.plan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trexense.data.utils.Result
import com.example.trexense.databinding.FragmentPlanBinding
import com.example.trexense.view.createPlan.CreatePlanActivity
import com.example.trexense.view.EventViewModelFactory

class PlanFragment : Fragment() {

    private var _binding: FragmentPlanBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlanViewModel by viewModels {
        EventViewModelFactory.getInstance(requireContext())
    }
    private lateinit var planAdapter: PlanAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        planAdapter = PlanAdapter { selectedPlan ->
            Toast.makeText(requireContext(), "Selected: ${selectedPlan.id}", Toast.LENGTH_SHORT)
                .show()
        }
        binding.rcPlan.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = planAdapter
        }
        viewModel.getPlans()
        observeViewModel()
        binding.add.setOnClickListener {
            val intent = Intent(requireContext(), CreatePlanActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
        viewModel.plansResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG)
                    .show()
                is Result.Success -> {
                    val plans = result.data.data
                    planAdapter.submitList(plans)
                    binding.rcPlan.visibility = View.VISIBLE
                    binding.imgPlan.visibility = View.GONE
                    binding.txtEmpty.visibility = View.GONE
                    binding.btnCreate.visibility = View.GONE
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPlans()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}