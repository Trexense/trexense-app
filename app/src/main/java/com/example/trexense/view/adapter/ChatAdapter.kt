package com.example.trexense.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.trexense.data.models.ChatMessage
import com.example.trexense.databinding.FragmentChatBinding
import com.example.trexense.databinding.ItemMessageBotBinding
import com.example.trexense.databinding.ItemMessageLocalBinding

class ChatAdapter : ListAdapter<ChatMessage, ChatAdapter.ChatViewHolder>(ChatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = when (viewType) {
            VIEW_TYPE_USER -> ItemMessageLocalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            else -> ItemMessageBotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatMessage = getItem(position) // Ambil item chat berdasarkan posisi
        holder.bind(chatMessage)
    }

    override fun getItemViewType(position: Int): Int {
        val chatMessage = getItem(position)
        return if (chatMessage.isUserMessage) VIEW_TYPE_USER else VIEW_TYPE_BOT
    }

    inner class ChatViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: ChatMessage) {
            if (binding is ItemMessageLocalBinding) {
                binding.tvUserMessageText.text = chatMessage.message
            } else if (binding is ItemMessageBotBinding) {
                binding.tvUserMessageText.text = chatMessage.message
            }
        }
    }

    class ChatDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val VIEW_TYPE_USER = 0
        const val VIEW_TYPE_BOT = 1
    }
}

