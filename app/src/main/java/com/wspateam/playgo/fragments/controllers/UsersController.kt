package com.wspateam.playgo.fragments.controllers

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.wspateam.playgo.R
import com.wspateam.playgo.adapters.UsersRecyclerAdapter
import com.wspateam.playgo.fragments.UsersFragment
import com.wspateam.playgo.models.User
import java.util.*

class UsersController(val usersFragment: UsersFragment): BaseController(usersFragment)
{
	val allUsers = mutableListOf<User>()

	fun showUsers()
	{
		val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_FragmentUsers)
		dbRef.child("users").addValueEventListener(object : ValueEventListener
		{
			override fun onCancelled(error: DatabaseError)
			{
			}

			override fun onDataChange(snapshot: DataSnapshot)
			{
				allUsers.clear()
				snapshot.children.forEach {
					val user = it.getValue(User::class.java)!!
					if (authIns.currentUser?.uid!! != user.uid)
					{
						allUsers.add(user)
					}
				}
				recyclerView.layoutManager = LinearLayoutManager(view.context)
				recyclerView.setHasFixedSize(true)
				recyclerView.adapter = UsersRecyclerAdapter(allUsers)
			}
		})
	}

	fun filterUsers(text: String)
	{
		val temp: MutableList<User> = mutableListOf()
		for (user in allUsers)
		{
			if (user.username!!.toLowerCase(Locale.ROOT).contains(text))
			{
				temp.add(user)
			}
		}
		val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_FragmentUsers)
		recyclerView.adapter = UsersRecyclerAdapter(temp)
		(recyclerView.adapter as UsersRecyclerAdapter).notifyDataSetChanged()
	}

	fun onUserDisconnect()
	{
		dbRef.child("users").child(authIns.currentUser!!.uid).child("status").onDisconnect().setValue("offline")
	}
}