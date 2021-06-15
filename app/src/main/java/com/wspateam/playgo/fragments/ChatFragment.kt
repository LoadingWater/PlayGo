package com.wspateam.playgo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wspateam.playgo.R
import com.wspateam.playgo.androidutilities.AndroidUtilities
import com.wspateam.playgo.fragments.controllers.ChatController

class ChatFragment : Fragment()
{
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val controller = ChatController(this)
        controller.populateFragmentFromArgs()
        controller.createChatIfNeeded()
        controller.onUserDisconnect()
        view.findViewById<ImageButton>(R.id.sendMessage_fragmentChat).setOnClickListener {
            controller.sendMessage()
        }
        view.findViewById<RecyclerView>(R.id.recyclerView_fragmentChat).setOnClickListener {
            AndroidUtilities.AndroidUtilities.hideKeyboard(it)
        }
        view.findViewById<EditText>(R.id.multilineText_fragmentChat).setOnClickListener {
            controller.liftRecyclerView()
        }

        view.findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean
            {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean
            {
                if (newText.isNullOrBlank())
                {
                    controller.liftRecyclerView()
                }
                controller.filterMessages(newText!!)
                return true
            }
        })

    }
}