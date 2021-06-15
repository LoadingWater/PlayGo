package com.wspateam.playgo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.wspateam.playgo.R
import com.wspateam.playgo.fragments.UsersFragmentDirections
import com.wspateam.playgo.models.User


class UsersRecyclerAdapter(private var users: MutableList<User>): RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder>()
{

	/**
	 * Provide a reference to the type of views that you are using
	 * (custom ViewHolder).
	 */
	class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
	{
		lateinit var opponent: User
		val username: TextView = view.findViewById(R.id.username_UserItem)
		val startChat: ImageButton = view.findViewById(R.id.startChat_UserItem)
		init
		{
			// Define click listener for the ViewHolder's View.
			startChat.setOnClickListener {
				val action = UsersFragmentDirections.actionUsersFragmentToChatFragment(opponent)
				Navigation.findNavController(view).navigate(action)
			}
		}
	}

	// Create new views (invoked by the layout manager)
	override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder
	{
		// Create a new view, which defines the UI of the list item
		val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.user_item, viewGroup, false)
		return ViewHolder(view)
	}

	// Replace the contents of a view (invoked by the layout manager)
	override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
	{
		// Get element from your dataset at this position and replace the
		// contents of the view with that element
		viewHolder.opponent = users[position]
		viewHolder.username.text = users[position].username
		// Setting status drawable

		val statusImage = if (users[position].status == "online") AppCompatResources.getDrawable(viewHolder.username.context, R.drawable.ic_round_circle_12_green) else AppCompatResources.getDrawable(viewHolder.username.context, R.drawable.ic_round_circle_12_red)
		viewHolder.username.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(viewHolder.username.context, R.drawable.ic_outline_account_circle_24_white), null, statusImage, null)
	}

	// Return the size of your dataset (invoked by the layout manager)
	override fun getItemCount() = users.size
}
