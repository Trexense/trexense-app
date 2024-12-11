package com.example.trexense.view.main.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.trexense.R
import com.example.trexense.data.pref.UserModel
import com.example.trexense.data.utils.Result
import com.example.trexense.databinding.FragmentHomeBinding
import com.example.trexense.databinding.FragmentProfileBinding
import com.example.trexense.view.ViewModelFactory
import com.example.trexense.view.main.home.HomeViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var idUser: String
    private lateinit var token: String

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

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user !=null ) {
                binding.editTextNameLayout.editText?.setText(user.name)
                binding.editTextEmailLayout.editText?.setText(user.email)
                idUser = user.userId
                token = user.token
            }
        }

        binding.btnSave.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()

            Log.d("USERID", "userID : $idUser ")
            viewModel.userUpdate(idUser, name, email)
            observeViewModel()
            viewModel.saveSession(UserModel(idUser, name, email, token))
        }

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireActivity()).apply {
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

    private fun observeViewModel() {
        viewModel.isLoadingProfile.observe(viewLifecycleOwner ) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.updateUser.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(requireActivity(), result.error , Toast.LENGTH_SHORT).show()
                is Result.Success -> Toast.makeText(requireActivity(), "Success Update", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.progressBar.visibility = if(loading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        fun newInstance() = ProfileFragment()
    }
}