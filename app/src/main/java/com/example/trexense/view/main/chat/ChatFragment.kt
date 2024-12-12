package com.example.trexense.view.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trexense.data.models.ChatMessage
import com.example.trexense.data.pref.UserPreference
import com.example.trexense.data.pref.dataStore
import com.example.trexense.data.repository.ChatRepository
import com.example.trexense.data.retrofit.ApiConfig
import com.example.trexense.data.utils.Result
import com.example.trexense.databinding.FragmentChatBinding
import com.example.trexense.view.adapter.ChatAdapter
import com.example.trexense.view.viewmodel.ChatViewModel
import com.example.trexense.view.viewmodel.ChatViewModelFactory

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val chatRepository by lazy {
        val apiService = ApiConfig.getApiService()
        val userPreference = UserPreference.getInstance(requireContext().dataStore)
        ChatRepository.getInstance(apiService, userPreference)
    }
    private val chatViewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(chatRepository)
    }

    private val chatAdapter by lazy { ChatAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSendButton()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatAdapter
        }
    }

    private fun setupSendButton() {
        binding.btnSend.setOnClickListener {
            val message = binding.textBoxChat.text.toString().trim()
            if (message.isNotEmpty()) {
                val userMessage = ChatMessage(id = System.currentTimeMillis().toString(), message = message, isUserMessage = true)
                chatAdapter.submitList(chatAdapter.currentList + userMessage)
                binding.textBoxChat.text?.clear()
                chatViewModel.sendMessage(message)
            } else {
                Toast.makeText(requireContext(), "Message cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        chatViewModel.chatResponse.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    // loading belum mir
                }
                is Result.Success -> {
                    val botResponse = result.data.data.response.orEmpty()

                    val formattedResponses = botResponse.split(Regex("\\n\\*\\s"))

                    val messages = formattedResponses.mapNotNull { paragraph ->
                        if (paragraph.isNotBlank()) {
                            ChatMessage(
                                id = System.currentTimeMillis().toString(),
                                message = paragraph.trim().replace("**", ""),
                                isUserMessage = false
                            )
                        } else null
                    }

                    chatAdapter.submitList(chatAdapter.currentList + messages)
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
