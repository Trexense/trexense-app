package com.example.trexense.view.main.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.layout.Layout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.trexense.R
import com.example.trexense.data.models.ImageItem
import com.example.trexense.databinding.FragmentHomeBinding
import com.example.trexense.view.adapter.ImageAdapter
import com.google.android.material.tabs.TabLayoutMediator
import java.util.UUID

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
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
//        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.viewpager2

        setUpTabLayoutWithViewPager()

        val imageList = arrayListOf(
            ImageItem(
                UUID.randomUUID().toString(),
                "https://fastly.picsum.photos/id/866/500/500.jpg?hmac=FOptChXpmOmfR5SpiL2pp74Yadf1T_bRhBF1wJZa9hg"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "https://fastly.picsum.photos/id/270/500/500.jpg?hmac=MK7XNrBrZ73QsthvGaAkiNoTl65ZDlUhEO-6fnd-ZnY"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "https://fastly.picsum.photos/id/320/500/500.jpg?hmac=2iE7TIF9kIqQOHrIUPOJx2wP1CJewQIZBeMLIRrm74s"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "https://fastly.picsum.photos/id/798/500/500.jpg?hmac=Bmzk6g3m8sUiEVHfJWBscr2DUg8Vd2QhN7igHBXLLfo"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "https://fastly.picsum.photos/id/95/500/500.jpg?hmac=0aldBQ7cQN5D_qyamlSP5j51o-Og4gRxSq4AYvnKk2U"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "https://fastly.picsum.photos/id/778/500/500.jpg?hmac=jZLZ6WV_OGRxAIIYPk7vGRabcAGAILzxVxhqSH9uLas"
            )
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
            handler.postDelayed(runnable, 3000) // Delay 3 detik
        }
        handler.postDelayed(runnable, 3000) // Delay awal 3 detik
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
    }
}