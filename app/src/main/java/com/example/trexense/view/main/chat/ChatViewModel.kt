package com.example.trexense.view.viewmodel

import androidx.lifecycle.*
import com.example.trexense.data.repository.ChatRepository
import com.example.trexense.data.response.ChatResponse
import com.example.trexense.data.utils.Result
import kotlinx.coroutines.launch

class ChatViewModel(private val chatRepository: ChatRepository) : ViewModel() {

    private val _chatResponse = MutableLiveData<Result<ChatResponse>>()
    val chatResponse: LiveData<Result<ChatResponse>> = _chatResponse

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _chatResponse.value = Result.Loading
            val result = chatRepository.getChatResponse(message)
            _chatResponse.value = result
        }
    }
}

// Factory class to provide the repository
class ChatViewModelFactory(private val chatRepository: ChatRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(chatRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
