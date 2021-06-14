package com.wspateam.playgo.fragments.controllers

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.wspateam.playgo.R
import com.wspateam.playgo.adapters.PostsRecyclerAdapter
import com.wspateam.playgo.androidutilities.AndroidUtilities.AndroidUtilities.hideKeyboard
import com.wspateam.playgo.fragments.RoomsFragment
import com.wspateam.playgo.models.Post
import com.wspateam.playgo.models.User
import com.wspateam.playgo.viewmodels.SharedApplicationViewModel
import java.text.SimpleDateFormat
import java.util.*


class RoomsController(val roomsFragment: RoomsFragment)
{
    private val TAG = RoomsController::class.java.simpleName
    private val sharedViewModel = ViewModelProvider(roomsFragment).get(SharedApplicationViewModel::class.java)
    private val dbRef = sharedViewModel.firebaseDatabaseInstance.value?.reference ?: FirebaseDatabase.getInstance().reference
    private val view = roomsFragment.requireView()
    private val activity = roomsFragment.requireActivity()

    private var upperRowSelectedView = view.findViewById<TextView>(R.id.popularRoomsFragment)
    private var lowerRowSelectedView = view.findViewById<TextView>(R.id.todayLowerRoomsFragment)

    fun pushPostFromCreationViewToFirebase()
    {
        val currentDateAndTime: String = SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss").format(Date())
        val authorUid = sharedViewModel.currentUser.value!!.uid
        val username = sharedViewModel.currentUser.value!!.username
        val postUid = dbRef.child("posts").push().key
        val game = view.findViewById<Spinner>(R.id.spinnerRoomsFragment).selectedItem.toString()
        val title = view.findViewById<EditText>(R.id.subjectFieldRoomsFragment).editableText.toString().trim()
        val body = view.findViewById<EditText>(R.id.messageRoomsFragment).editableText.toString().trim()
        if ( authorUid.isNullOrEmpty())
        {
            Toast.makeText(view.context, "Couldn't get authorUid.", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Couldn't get authorUid.")
            return
        }
        if (postUid.isNullOrEmpty())
        {
            Toast.makeText(view.context, "Couldn't create postUid.", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Couldn't create postUid.")
            return
        }
        if (title.isEmpty())
        {
            Toast.makeText(view.context, "Subject is empty.", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Subject is empty.")
            return
        }
        if (body.isEmpty())
        {
            Toast.makeText(view.context, "Body is empty.", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Body is empty.")
            return
        }

        val post = Post(postUid, game, username, title, body, currentDateAndTime)
        dbRef.child("posts").child(postUid).setValue(post).addOnCompleteListener {
            if (it.isSuccessful)
            {
                cleanAfterPostCreation()
                Toast.makeText(view.context, "Post created.", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "Post pushed to the db.")
            }
            else
            {
                Toast.makeText(view.context, "Post wasn't created.", Toast.LENGTH_LONG).show()
                Log.e(TAG, "Couldn't push the post to the db.")
            }
        }
    }

    fun getPostsFromFirebase(game: String = "")
    {
        val post = dbRef.child("posts").addValueEventListener(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError)
            {

            }
            override fun onDataChange(snapshot: DataSnapshot)
            {
                val posts = mutableListOf<Post>()
                snapshot.children?.forEach {
                    val post = it.getValue(Post::class.java)
                    if (post != null)
                    {
                        //If an argument was given
                        if (game.isNotBlank() && post.game == game)
                        {
                            posts.add(post)
                        }
                        else if (game.isBlank())
                        {
                            posts.add(post)
                        }
                    }
                }
                view.findViewById<RecyclerView>(R.id.recyclerViewRoomsFragment).adapter = PostsRecyclerAdapter(posts)
            }
        })
    }

    fun setUpRecyclerAdapter()
    {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewRoomsFragment)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
    }

    fun showCreationForm()
    {
        val createView = view.findViewById<ConstraintLayout>(R.id.creationFormRoomsFragment)
        val slideUp = AnimationUtils.loadAnimation(view.context, R.anim.slide_up_animation)
        createView.visibility = View.VISIBLE
        createView.startAnimation(slideUp)
    }

    fun hideCreationForm()
    {
        val createView = view.findViewById<ConstraintLayout>(R.id.creationFormRoomsFragment)
        val slideDown = AnimationUtils.loadAnimation(view.context, R.anim.slide_down_animation)
        createView.startAnimation(slideDown)
        createView.visibility = View.INVISIBLE
    }

    private fun clearCreationForm()
    {
        view.findViewById<EditText>(R.id.subjectFieldRoomsFragment).editableText.clear()
        view.findViewById<EditText>(R.id.messageRoomsFragment).editableText.clear()
    }

    private fun cleanAfterPostCreation()
    {
        clearCreationForm()
        hideKeyboard(view)
        hideCreationForm()
    }

    fun setUpUpperRowSelection()
    {
        view.findViewById<TextView>(R.id.popularRoomsFragment).setOnClickListener {
            changeUpperRowSelection(it as TextView)
        }
        view.findViewById<TextView>(R.id.recommendedRoomsFragment).setOnClickListener {
            changeUpperRowSelection(it as TextView)
        }
        view.findViewById<TextView>(R.id.favoriteRoomsFragment).setOnClickListener {
            changeUpperRowSelection(it as TextView)
        }
    }

    fun setUpLowerRowSelection()
    {
        view.findViewById<TextView>(R.id.todayLowerRoomsFragment).setOnClickListener {
            changeLowerRowSelection(it as TextView)
        }
        view.findViewById<TextView>(R.id.popularLowerRoomsFragment).setOnClickListener {
            changeLowerRowSelection(it as TextView)
        }
        view.findViewById<TextView>(R.id.favoriteLowerRoomsFragment).setOnClickListener {
            changeLowerRowSelection(it as TextView)
        }
    }

    private fun changeUpperRowSelection(clickedView: TextView)
    {
        upperRowSelectedView.background = null
        clickedView.background = view.resources.getDrawable(R.drawable.rounded_rect_yellow)
        upperRowSelectedView = clickedView
    }

    private fun changeLowerRowSelection(clickedView: TextView)
    {
        lowerRowSelectedView.background = null
        clickedView.background = view.resources.getDrawable(R.drawable.rounded_rect_yellow)
        lowerRowSelectedView = clickedView
    }

    fun populateSpinner()
    {
        val spinner = view.findViewById<Spinner>(R.id.spinnerRoomsFragment)
        val adapter = ArrayAdapter.createFromResource(view.context, R.array.games, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun fetchCurrentUserData()
    {
        val currentUserUid = sharedViewModel.firebaseAuthInstance.value!!.currentUser!!.uid
        dbRef.child("users").child(currentUserUid).get().addOnCompleteListener {
            if (it.isSuccessful)
            {
                sharedViewModel.currentUser.value = it.result!!.getValue(User::class.java)!!
            }
            else
            {
                Log.e(TAG, "")
            }
        }
    }
}