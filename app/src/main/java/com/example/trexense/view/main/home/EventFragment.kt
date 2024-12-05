 package com.example.trexense.view.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.trexense.R
import com.example.trexense.data.models.EventItem
import com.example.trexense.data.models.HotelItem
import com.example.trexense.databinding.FragmentEventBinding
import com.example.trexense.databinding.FragmentHotelBinding
import com.example.trexense.view.adapter.ListEventAdapter
import com.example.trexense.view.adapter.ListHotelAdapter

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

        val eventList = arrayListOf(
            EventItem(
                id = "1",
                name = "Event 1",
                price = "Rp. 540.000, 00",
                place = "Denpasar, Bali",
                image = resources.getIdentifier("img_event", "drawable", context?.packageName)
            ),
            EventItem(
                id = "2",
                name = "Event 2",
                price = "Rp. 800.000, 00",
                place = "Denpasar, Bali",
                image = resources.getIdentifier("image1", "drawable", context?.packageName)
            ),
            EventItem(
                id = "3",
                name = "Event 3",
                price = "Rp. 1000.000, 00",
                place = "Denpasar, Bali",
                image = resources.getIdentifier("image2", "drawable", context?.packageName)
            ),
            EventItem(
                id = "3",
                name = "Event 4",
                price = "Rp. 1000.000, 00",
                place = "Denpasar, Bali",
                image = resources.getIdentifier("image3", "drawable", context?.packageName)
            )
        )

        val eventAdapter = ListEventAdapter()
        binding.rvHotel.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            setHasFixedSize(true)
            adapter = eventAdapter
            eventAdapter.submitList(eventList)
        }

    }

}