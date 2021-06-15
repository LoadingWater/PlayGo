package com.wspateam.playgo.fragments.controllers

import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.wspateam.playgo.R
import com.wspateam.playgo.adapters.MessagesRecyclerViewAdapter
import com.wspateam.playgo.fragments.ChatFragment
import com.wspateam.playgo.fragments.ChatFragmentArgs
import com.wspateam.playgo.models.Chat
import com.wspateam.playgo.models.Message
import java.text.SimpleDateFormat
import java.util.*

class ChatController(val chatFragment: ChatFragment) : BaseController(chatFragment)
{
	private val allMessages = mutableListOf<Message>()
	private val opponent = ChatFragmentArgs.fromBundle(chatFragment.requireArguments()).opponent
	private val currentUserUid = authIns.currentUser?.uid!!
	private lateinit var currentChatUid: String

	fun populateFragmentFromArgs()
	{
		val username = view.findViewById<TextView>(R.id.username_fragmentChat)
		username.text = opponent.username
		val statusImage = if (opponent.status == "online") AppCompatResources.getDrawable(view.context, R.drawable.ic_round_circle_12_green) else AppCompatResources.getDrawable(view.context, R.drawable.ic_round_circle_12_red)
		username.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(view.context, R.drawable.ic_outline_account_circle_24_white), null, statusImage, null)
	}

	fun sendMessage()
	{
		val multilineText = view.findViewById<EditText>(R.id.multilineText_fragmentChat)
		val message = Message()
		message.chatUid = currentChatUid
		message.messageUid = dbRef.child("users").child(currentUserUid).child("chats").child(message.chatUid!!).child("messages").push().key
		message.senderUid = currentUserUid
		message.messageText = multilineText.text.trim().toString()
		message.timeStamp = SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss").format(Date())
		if (message.messageText!!.isNotBlank())
		{
			// Push to currentUser and opponent
			dbRef.child("users").child(currentUserUid).child("chats").child(message.chatUid!!).child("messages").push().setValue(message)
			dbRef.child("users").child(opponent.uid!!).child("chats").child(message.chatUid!!).child("messages").push().setValue(message)
			multilineText.text.clear()
		}
	}

	private fun hookRecyclerViewToMessages()
	{
		val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_fragmentChat)
		recyclerView.layoutManager = LinearLayoutManager(view.context)
		recyclerView.setHasFixedSize(true)
		val a = LinearLayoutManager(view.context)
		dbRef.child("users").child(currentUserUid).child("chats").child(currentChatUid).child("messages").addValueEventListener(object : ValueEventListener
		{
			override fun onCancelled(error: DatabaseError)
			{
			}

			override fun onDataChange(snapshot: DataSnapshot)
			{
				allMessages.clear()
				snapshot.children.forEach {
					allMessages.add(it.getValue(Message::class.java)!!)
					Log.i(TAG, "message added.")
				}
				recyclerView.adapter = MessagesRecyclerViewAdapter(allMessages)
				val position = if (allMessages.size - 1 >= 0) allMessages.size else 0
				recyclerView.smoothScrollToPosition(position)
				//view.findViewById<ConstraintLayout>(R.id.root_fragmentChat).visibility = View.VISIBLE
			}
		})
	}

	fun liftRecyclerView()
	{
		val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_fragmentChat)
		val position = if (allMessages.size - 1 >= 0) allMessages.size - 1 else 0
		recyclerView.smoothScrollToPosition(position)
	}

	fun createChatIfNeeded()
	{
		val currentUserUid = authIns.currentUser?.uid!!

		dbRef.child("users").child(currentUserUid).child("chats").get().addOnSuccessListener { currentUserChats: DataSnapshot ->
			dbRef.child("users").child(opponent.uid!!).child("chats").get().addOnSuccessListener { opponentUserChats: DataSnapshot ->
				val chatUid = dbRef.child("users").child(currentUserUid).child("chats").push().key!!
				val newChat = Chat(chatUid, mutableListOf())
				// If both users have chats that are not null
				if (currentUserChats.exists() && opponentUserChats.exists())
				{
					// Compare all chatUids from both users.
					currentUserChats.children.forEach { currentUserChatsUids: DataSnapshot ->
						opponentUserChats.children.forEach { opponentChatsUids: DataSnapshot ->
							if (currentUserChatsUids.key == opponentChatsUids.key)
							{
								currentChatUid = currentUserChatsUids.key.toString()
								Log.i("chats", "chat exists")
							}
						}
					}
					// If non was found create one
					if (!this::currentChatUid.isInitialized)
					{
						dbRef.child("users").child(currentUserUid).child("chats").child(chatUid).setValue(newChat)
						dbRef.child("users").child(opponent.uid!!).child("chats").child(chatUid).setValue(newChat)
						currentChatUid = chatUid
					}
				} else
				{
					dbRef.child("users").child(currentUserUid).child("chats").child(chatUid).setValue(newChat)
					dbRef.child("users").child(opponent.uid!!).child("chats").child(chatUid).setValue(newChat)
					currentChatUid = chatUid
				}
				hookRecyclerViewToMessages()
			}
		}
	}

	fun filterMessages(text: String)
	{
		val temp: MutableList<Message> = mutableListOf()
		for (message in allMessages)
		{
			if (message.messageText!!.toLowerCase(Locale.ROOT).contains(text))
			{
				temp.add(message)
			}
		}
		val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_fragmentChat)
		recyclerView.adapter = MessagesRecyclerViewAdapter(temp)
		(recyclerView.adapter as MessagesRecyclerViewAdapter).notifyDataSetChanged()
	}

	fun onUserDisconnect()
	{
		dbRef.child("users").child(currentUserUid).child("status").onDisconnect().setValue("offline")
	}
}