package com.antoniocostadossantos.chat_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.antoniocostadossantos.chat_app.databinding.MessageItemBinding
import com.antoniocostadossantos.chat_app.model.MessageModel

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var messages = mutableListOf<MessageModel>()

    fun setItems(item: MessageModel) {
        messages.add(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    inner class MessageViewHolder(binding: MessageItemBinding) : ViewHolder(binding.root) {

        val user = binding.tvUsername
        val message = binding.tvMessage

        fun bind(messageModel: MessageModel) {
            user.text = messageModel.user
            message.text = messageModel.message
        }
    }
}