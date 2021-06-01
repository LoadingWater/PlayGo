package com.wspateam.playgo.fragments.controllers

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.wspateam.playgo.R
import com.wspateam.playgo.adapters.RepliesRecyclerAdapter
import com.wspateam.playgo.androidutilities.AndroidUtilities
import com.wspateam.playgo.fragments.ForumPostFragment
import com.wspateam.playgo.fragments.ForumPostFragmentArgs
import com.wspateam.playgo.models.Post
import com.wspateam.playgo.models.Reply
import com.wspateam.playgo.models.User
import com.wspateam.playgo.viewmodels.SharedApplicationViewModel
import java.text.SimpleDateFormat
import java.util.*

class ForumPostController(val forumPostFragment: ForumPostFragment)
{
    private val TAG = ForumPostController::class.java.simpleName
    private val sharedViewModel = ViewModelProvider(forumPostFragment).get(SharedApplicationViewModel::class.java)
    private val dbRef = sharedViewModel.firebaseDatabaseInstance.value?.reference ?: FirebaseDatabase.getInstance().reference
    private val args: ForumPostFragmentArgs = ForumPostFragmentArgs.fromBundle(forumPostFragment.requireArguments())
    private val view = forumPostFragment.requireView()
    private val activity = forumPostFragment.requireActivity()

    fun showPost()
    {
        val title = view.findViewById<TextView>(R.id.titleForumPostFragment)
        val autor = view.findViewById<TextView>(R.id.authorForumPostFragment)
        val body = view.findViewById<TextView>(R.id.bodyForumPostFragment)
        val date = view.findViewById<TextView>(R.id.dateForumPostFragment)

        val post = args.postClass

        title.text = post.title
        autor.text = post.author
        body.text = post.body
        date.text = post.date

        getRepliesFromFirebase(args.postClass.uid!!)
    }

    fun addReplyToPost()
    {
        val replyUid = dbRef.child("replies").push().key
        val postUid = args.postClass.uid
        val authorUid = sharedViewModel.firebaseAuthInstance.value?.currentUser?.uid
        lateinit var authorName: String
        val body = view.findViewById<EditText>(R.id.replyTextForumPostFragment).editableText.toString().trim()
        val currentDateAndTime: String = SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss").format(Date())

        if (replyUid.isNullOrEmpty())
        {
            Toast.makeText(view.context, "Couldn't create replyUid.", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Couldn't create replyUid.")
            return
        }
        if ( authorUid.isNullOrEmpty())
        {
            Toast.makeText(view.context, "Couldn't get authorUid.", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Couldn't get authorUid.")
            return
        }
        if (body.isEmpty())
        {
            Toast.makeText(view.context, "Body is empty.", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Body is empty.")
            return
        }
        // Getting user name and proceed
        dbRef.child("users").child(authorUid).get().addOnCompleteListener {
            if (it.isSuccessful)
            {
                val user = it.result?.getValue(User::class.java)
                if (user != null)
                {
                    val reply = Reply(replyUid, postUid, user.username, body, currentDateAndTime )
                    dbRef.child("replies").child(replyUid).setValue(reply).addOnCompleteListener {
                        if (it.isSuccessful)
                        {
                            getRepliesFromFirebase(postUid!!)
                            cleanAfterPostCreation()
                            Toast.makeText(view.context, "Reply created.", Toast.LENGTH_SHORT).show()
                            Log.i(TAG, "Reply pushed to the db.")
                            dbRef.child("posts").child(postUid!!).get().addOnSuccessListener {
                                val post = it.getValue(Post::class.java)
                                dbRef.child("posts").child(postUid).child("repliesCount").setValue(post?.repliesCount?.plus(1))
                            }
                        }
                        else
                        {
                            Toast.makeText(view.context, "Reply wasn't created.", Toast.LENGTH_LONG).show()
                            Log.e(TAG, "Couldn't push the Reply to the db.")
                        }
                    }
                }
                else
                {
                    Log.e(TAG, "Couldn't cast result to User. ${it.exception?.message.toString()}")
                }
            }
            else
            {
                Log.e(TAG, "Couldn't get username from authorId.")
            }
        }
    }

    fun getRepliesFromFirebase(postUid: String)
    {
        val reply = dbRef.child("replies").get().addOnCompleteListener {
            if (it.isSuccessful)
            {
                val replies = mutableListOf<Reply>()
                it.result?.children?.forEach {
                    val reply = it.getValue(Reply::class.java)
                    if (reply != null && reply.postUid == postUid)
                    {
                        replies.add(reply)
                    }
                }
                setUpRecyclerAdapter(replies)
            }
            else
            {
                Log.e(TAG, "Failed to get \"posts\" from Firebase. ${it.exception?.message}")
            }
        }
    }

    private fun setUpRecyclerAdapter(replies: MutableList<Reply>)
    {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewForumPostFragment)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = RepliesRecyclerAdapter(replies)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
    }

    fun showReplyForm()
    {
        view.findViewById<Button>(R.id.showReplyFormButtonForumPostFragment).visibility = View.INVISIBLE
        val replyForm = view.findViewById<ConstraintLayout>(R.id.replyFormForumPostFragment)
        val slideUp = AnimationUtils.loadAnimation(view.context, R.anim.slide_up_animation)
        replyForm.visibility = View.VISIBLE
        replyForm.startAnimation(slideUp)
    }

    fun hideReplyForm()
    {
        view.findViewById<Button>(R.id.showReplyFormButtonForumPostFragment).visibility = View.VISIBLE
        val replyForm = view.findViewById<ConstraintLayout>(R.id.replyFormForumPostFragment)
        val slideDown = AnimationUtils.loadAnimation(view.context, R.anim.slide_down_animation)
        replyForm.startAnimation(slideDown)
        replyForm.visibility = View.INVISIBLE
    }

    private fun clearCreationForm()
    {
        view.findViewById<EditText>(R.id.replyTextForumPostFragment).editableText.clear()
    }

    private fun cleanAfterPostCreation()
    {
        clearCreationForm()
        AndroidUtilities.hideKeyboard(view)
        hideReplyForm()
    }
}