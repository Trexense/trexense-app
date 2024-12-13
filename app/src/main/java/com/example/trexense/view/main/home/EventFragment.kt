package com.example.trexense.view.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.cachedIn
import androidx.recyclerview.widget.GridLayoutManager
import com.example.trexense.R
import com.example.trexense.data.models.EventItem
import com.example.trexense.data.models.HotelItem
import com.example.trexense.databinding.FragmentEventBinding
import com.example.trexense.databinding.FragmentHotelBinding
import com.example.trexense.view.PageWelcome
import com.example.trexense.view.ViewModelFactory
import com.example.trexense.view.adapter.ListEventAdapter
import com.example.trexense.view.adapter.ListHotelAdapter
import com.example.trexense.view.main.createEvent.CreateEventActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventFragment : Fragment() {
    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventAdapter: ListEventAdapter
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventAdapter = ListEventAdapter()
        viewModel.isLoading.observe(viewLifecycleOwner) { value ->
            showLoading(value)
        }

        viewModel.getSession().observe(viewLifecycleOwner) {user ->
            Log.d("INFOTOKEN", "token: ${user?.token} ")
            if(user != null && user.isLogin && user.token.isNotEmpty()) {
                viewModel.getListEvent(user.token)
            } else {
                startActivity(Intent(requireActivity(), PageWelcome::class.java))
            }
        }

        loadDataEvent()

    }

    private fun loadDataEvent() {
        viewModel.dataevents.observe(viewLifecycleOwner) { event ->
            binding.rvHotel.apply {
                layoutManager = GridLayoutManager(requireActivity(), 2)
                setHasFixedSize(true)
                adapter = eventAdapter
            }
            eventAdapter.submitList(event)
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        binding.rvHotel.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            setHasFixedSize(true)
            adapter = eventAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        loadDataEvent()
    }

}