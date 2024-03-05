package com.antoniocostadossantos.chat_app.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.antoniocostadossantos.chat_app.databinding.ActivityMainBinding
import com.antoniocostadossantos.chat_app.model.MessageModel
import com.antoniocostadossantos.chat_app.ui.adapter.MessageAdapter
import com.antoniocostadossantos.chat_app.util.USERNAME_KEY
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userName: String
    private val database = FirebaseDatabase.getInstance().reference
    private val adapter = MessageAdapter()
    private val gson = Gson()
    private val message = MessageModel()
    private val messages = mutableListOf<MessageModel?>()
    private val nodeRef = database.child("messages")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        database.child("messages").push().child("name").setValue("Tonhera")

        getUserName()
        setupRecyclerView()
        observeMessages()

        binding.btnSendMessage.setOnClickListener {
            sendMessage()
        }

    }

    private fun observeMessages() {

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    for (snapshot in snapshot.children) {
                        Log.i("TESTE", snapshot.toString())
                        val message = snapshot.children.map {
                            it.getValue(MessageModel().javaClass)
                        }
                        messages.clear()
                        messages.add(message.last())
                        adapter.setItems(message.first()!!)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        this.nodeRef.addValueEventListener(listener)

    }

    private fun sendMessage() {
        message.user = userName
        message.message = binding.sendMessageInput.text.toString()
        this.nodeRef.removeValue()
        this.nodeRef.push().child("user").setValue(message)
        binding.sendMessageInput.setText("")
    }

    private fun setupRecyclerView() {
        val rv = binding.rvMessages
        rv.adapter = this.adapter
        rv.setHasFixedSize(true)
    }

    private fun getUserName() {
        userName = intent.getStringExtra(USERNAME_KEY) ?: "Anonymous"
    }


}