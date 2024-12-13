package com.example.trexense.view.main.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.trexense.R
import com.example.trexense.data.models.HotelItem
import com.example.trexense.data.utils.Result
import com.example.trexense.databinding.FragmentHotelBinding
import com.example.trexense.databinding.ItemHotelBinding
import com.example.trexense.view.PageWelcome
import com.example.trexense.view.ViewModelFactory
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

    private val viewModel by viewModels<HotelViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

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

//        val hoteList = arrayListOf(
//            HotelItem(
//                id = "1",
//                name = "Hotel Santika",
//                price = "Rp. 540.000, 00",
//                place = "Denpasar, Bali",
//                image = resources.getIdentifier("hotel_list", "drawable", context?.packageName)
//            ),
//            HotelItem(
//                id = "2",
//                name = "Hotel Bonjour",
//                price = "Rp. 800.000, 00",
//                place = "Denpasar, Bali",
//                image = resources.getIdentifier("hotel_list", "drawable", context?.packageName)
//            ),
//            HotelItem(
//                id = "3",
//                name = "Hotel Anggun",
//                price = "Rp. 1000.000, 00",
//                place = "Denpasar, Bali",
//                image = resources.getIdentifier("hotel_list", "drawable", context?.packageName)
//            )
//        )

        val listHotel = ListHotelAdapter()

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin && user.token.isEmpty()) {
                startActivity(Intent(requireActivity(), PageWelcome::class.java))
            } else {
                if (user.token.isNotEmpty()) {
                    viewModel.getHotelRecommendation().observe(viewLifecycleOwner) { hotelData ->
                        when(hotelData) {
                            is Result.Loading -> showLoading(true)
                            is Result.Error -> {
                                showLoading(false)
                                Toast.makeText(requireActivity(), hotelData.error, Toast.LENGTH_SHORT).show()
                            }
                            is Result.Success -> {
                                showLoading(false)
                                binding.rvHotel.apply {
                                    layoutManager = GridLayoutManager(requireActivity(), 2)
                                    setHasFixedSize(true)
                                    adapter = listHotel
                                    listHotel.submitList(hotelData.data.data)
                                }
                            }
                        }
                    }
                } else {
                    startActivity(Intent(requireActivity(), PageWelcome::class.java))
                }
            }
        }


    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }


}