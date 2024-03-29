package com.wspateam.playgo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.wspateam.playgo.R
import com.wspateam.playgo.fragments.controllers.ForumPostController

class ForumPostFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forum_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = ForumPostController(this)
        controller.showPost()
        view.findViewById<Button>(R.id.showReplyFormButtonForumPostFragment).setOnClickListener {
            controller.showReplyForm()
        }
        view.findViewById<Button>(R.id.closeReplyFormButtonForumPostFragment).setOnClickListener {
            controller.hideReplyForm()
        }
        view.findViewById<Button>(R.id.addReplyButtonForumPostFragment).setOnClickListener {
            controller.addReplyToPost()
        }
    }
}