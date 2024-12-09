package com.example.trexense.view.main.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.trexense.R
import com.example.trexense.databinding.FragmentHomeBinding
import com.example.trexense.databinding.FragmentProfileBinding
import com.example.trexense.view.ViewModelFactory
import com.example.trexense.view.main.home.HomeViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireActivity()).apply {
                setTitle("Logout Confirm !")
                setMessage("Do you want to logout ?")
                setPositiveButton("Logout") {_, _ ->
                    viewModel.logout()
                }
                setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                create()
                show()
            }
        }

    }

    private fun showLoading(loading: Boolean): Boolean {
        if (loading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
        return loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        fun newInstance() = ProfileFragment()
    }
}