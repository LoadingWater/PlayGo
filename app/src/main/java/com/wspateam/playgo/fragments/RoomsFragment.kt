package com.wspateam.playgo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.wspateam.playgo.R
import com.wspateam.playgo.fragments.controllers.RoomsController


class RoomsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rooms, container, false)
    }

    fun loadPosts()
    {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = RoomsController(this)

        controller.getPostsFromFirebase()

        val addDiscussion = view.findViewById<Button>(R.id.addDiscussionRoomsFragment)
        addDiscussion.setOnClickListener {
            controller.showCreationForm()
        }

        val closeButton = view.findViewById<Button>(R.id.closeCreationButtonRoomsFragment)
        closeButton.setOnClickListener {
            controller.hideCreationForm()
        }

        val addButton = view.findViewById<Button>(R.id.addButtonRoomsFragment)
        addButton.setOnClickListener {
            controller.pushPostFromCreationViewToFirebase()
        }

        controller.setUpUpperRowSelection()
        controller.setUpLowerRowSelection()
    }
}
/*
* val uid = FirebaseDatabase.getInstance().reference.child("posts").push().key!!
            controller.pushPostToFirebase(uid, "author", "title", "body")
* */