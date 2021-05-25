package com.wspateam.playgo.fragments.controllers

import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.wspateam.playgo.R
import com.wspateam.playgo.fragments.ForumPostFragment
import com.wspateam.playgo.fragments.ForumPostFragmentArgs
import com.wspateam.playgo.viewmodels.SharedApplicationViewModel

class ForumPostController(val forumPostFragment: ForumPostFragment)
{
    private val TAG = "ForumPostController"
    private val sharedViewModel = ViewModelProvider(forumPostFragment).get(SharedApplicationViewModel::class.java)
    private val args: ForumPostFragmentArgs = ForumPostFragmentArgs.fromBundle(forumPostFragment.requireArguments())
    private val view = forumPostFragment.requireView()
    private val activity = forumPostFragment.requireActivity()

    fun showPost()
    {
        val title = view.findViewById<TextView>(R.id.titleForumPostFragment)
        val autor = view.findViewById<TextView>(R.id.authorForumPostFragment)
        val body = view.findViewById<TextView>(R.id.bodyForumPostFragment)
        val date = view.findViewById<TextView>(R.id.dateForumPostFragment)

        title.text = args.title
        autor.text = args.author
        body.text = args.body
        date.text = args.date
    }
}