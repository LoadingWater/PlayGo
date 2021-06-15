package com.wspateam.playgo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wspateam.playgo.R
import com.wspateam.playgo.fragments.controllers.UsersController

class UsersFragment : Fragment()
{

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val controller = UsersController(this)
        controller.showUsers()
        controller.onUserDisconnect()

        view.findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView).setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean
            {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean
            {
                controller.filterUsers(newText!!)
                return true
            }
        })
    }
}