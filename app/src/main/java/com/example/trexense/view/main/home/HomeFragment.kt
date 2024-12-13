package com.example.trexense.view.main.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.trexense.R
import com.example.trexense.data.models.ImageItem
import com.example.trexense.databinding.FragmentHomeBinding
import com.example.trexense.view.PageWelcome
import com.example.trexense.view.ViewModelFactory
import com.example.trexense.view.adapter.ImageAdapter
import com.example.trexense.view.main.search.SearchActivity
import com.google.android.material.tabs.TabLayoutMediator
import java.util.UUID

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,0,8,0)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val factory = ViewModelFactory.getInstance(requireActivity())
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        viewPager = binding.viewpager2

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.search.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
        setUpTabLayoutWithViewPager()

        homeViewModel.getSession().observe(viewLifecycleOwner, { user ->
            if(!user.isLogin) {
                startActivity(Intent(requireActivity(), PageWelcome::class.java))
            } else {
                val userLogin = user.name ?: "User"
                val setName = String.format(getString(R.string.text_hello), userLogin)
                binding.tvHello.text = setName
            }
        })

        val imageList = arrayListOf(
            ImageItem(
                UUID.randomUUID().toString(),
                "https://storage.googleapis.com/trexense-capstone/banners/1734016948637-ad928eb5.png"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "https://storage.googleapis.com/trexense-capstone/banners/1734017164580-e5dfdba6.png"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "https://storage.googleapis.com/trexense-capstone/banners/1734015960268-24d340c5.png"
            ),
        )

        val imageAdapter = ImageAdapter()
        viewPager.adapter = imageAdapter
        imageAdapter.submitList(imageList)

        val slideDotLL = binding.slideDotLL
        val dotsImage = Array(imageList.size) { ImageView(requireActivity())}

        dotsImage.forEach {
            it.setImageResource(
                R.drawable.non_active_dot
            )
            slideDotLL.addView(it,params)
        }

        dotsImage[0].setImageResource(R.drawable.active_dot)

        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dotsImage.mapIndexed { index, imageView ->
                    if (position == index) {
                        imageView.setImageResource(
                            R.drawable.active_dot
                        )
                    }else {
                        imageView.setImageResource(R.drawable.non_active_dot)
                    }
                }
                super.onPageSelected(position)
            }
        }

        viewPager.registerOnPageChangeCallback(pageChangeListener)

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            val currentItem = viewPager.currentItem
            val totalItems = imageAdapter.itemCount
            val nextItem = (currentItem + 1) % totalItems
            viewPager.setCurrentItem(nextItem, true)
            handler.postDelayed(runnable, 5000) // Delay 3 detik
        }
        handler.postDelayed(runnable, 7000) // Delay awal 3 detik
    }

    private fun setUpTabLayoutWithViewPager() {
        binding.viewPagerTab.adapter = DashboardPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPagerTab) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        for (i in 0..2) {
            val textView = LayoutInflater.from(requireContext()).inflate(R.layout.tab_title, null)
                as TextView
            binding.tabLayout.getTabAt(i)?.customView = textView
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewPager.unregisterOnPageChangeCallback(pageChangeListener)
    }

    companion object {
        private val tabTitles = arrayListOf("Hotel", "Event")
        private const val TAG = "HomeFragment"
    }
}