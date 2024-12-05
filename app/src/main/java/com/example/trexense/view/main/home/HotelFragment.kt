package com.example.trexense.view.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.trexense.R
import com.example.trexense.data.models.HotelItem
import com.example.trexense.databinding.FragmentHotelBinding
import com.example.trexense.databinding.ItemHotelBinding
import com.example.trexense.view.adapter.ListHotelAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HotelFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HotelFragment : Fragment() {

    private var _binding: FragmentHotelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHotelBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hoteList = arrayListOf(
            HotelItem(
                id = "1",
                name = "Hotel Santika",
                price = "Rp. 540.000, 00",
                place = "Denpasar, Bali",
                image = resources.getIdentifier("hotel_list", "drawable", context?.packageName)
            ),
            HotelItem(
                id = "2",
                name = "Hotel Bonjour",
                price = "Rp. 800.000, 00",
                place = "Denpasar, Bali",
                image = resources.getIdentifier("hotel_list", "drawable", context?.packageName)
            ),
            HotelItem(
                id = "3",
                name = "Hotel Anggun",
                price = "Rp. 1000.000, 00",
                place = "Denpasar, Bali",
                image = resources.getIdentifier("hotel_list", "drawable", context?.packageName)
            )
        )

        val listHotel = ListHotelAdapter()
        binding.rvHotel.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            setHasFixedSize(true)
            adapter = listHotel
            listHotel.submitList(hoteList)
        }

    }


}