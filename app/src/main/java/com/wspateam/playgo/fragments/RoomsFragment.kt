package com.wspateam.playgo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = RoomsController(this)

        controller.fetchCurrentUserData()
        controller.populateSpinner()
        controller.getPostsFromFirebase()
        controller.setUpRecyclerAdapter()
        controller.onUserDisconnect()

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

        val showUsersButton = view.findViewById<Button>(R.id.showUsersRoomsFragment).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.usersFragment)
        }

        val games = resources.getStringArray(R.array.games)
        val lolGame = view.findViewById<FrameLayout>(R.id.lolGameRoomsFragment).setOnClickListener {
            controller.getPostsFromFirebase(games[0])
        }
        val wotGame = view.findViewById<FrameLayout>(R.id.wotGameRoomsFragment).setOnClickListener {
            controller.getPostsFromFirebase(games[1])
        }
        val fortniteGame = view.findViewById<FrameLayout>(R.id.fortniteGameRoomsFragment).setOnClickListener {
            controller.getPostsFromFirebase(games[2])
        }
        val dota2Game = view.findViewById<FrameLayout>(R.id.dota2GameRoomsFragment).setOnClickListener {
            controller.getPostsFromFirebase(games[3])
        }

        controller.setUpUpperRowSelection()
        controller.setUpLowerRowSelection()
    }
}