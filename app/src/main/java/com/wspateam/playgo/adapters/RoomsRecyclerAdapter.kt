package com.wspateam.playgo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.wspateam.playgo.R
import com.wspateam.playgo.fragments.RoomsFragmentDirections
import com.wspateam.playgo.models.Post

class RoomsRecyclerAdapter(private val posts: MutableList<Post>): RecyclerView.Adapter<RoomsRecyclerAdapter.ViewHolder>()
{
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var rootLayout: ConstraintLayout = view.findViewById<ConstraintLayout>(R.id.rootLayoutForumPostItem)
        var postUid: String = ""
        var postTitle: TextView = view.findViewById<TextView>(R.id.titleForumPostItem)
        var postAuthor: TextView = view.findViewById<TextView>(R.id.authorForumPostItem)
        var postBody: String = ""
        var postDate: TextView = view.findViewById<TextView>(R.id.dateForumPostItem)
        var postRepliesCount: TextView = view.findViewById(R.id.repliesCountForumPostItem)

        init
        {
            rootLayout.setOnClickListener {
                Toast.makeText(view.context, "Clicked on post.", Toast.LENGTH_SHORT).show()
                val action = RoomsFragmentDirections.actionRoomsFragmentToForumPostFragment(
                    postUid,
                    postTitle.text.toString(),
                    postAuthor.text.toString(),
                    postBody,
                    postDate.text.toString()
                    )
            Navigation.findNavController(view).navigate(action)
            }
        }
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder
    {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.forum_post_item, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
    {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.postUid = posts[position].uid ?: "N/A"
        viewHolder.postTitle.text = posts[position].title ?: "N/A"
        viewHolder.postAuthor.text = posts[position].author ?: "N/A"
        viewHolder.postBody = posts[position].body ?: "N/A"
        viewHolder.postDate.text = posts[position].date ?: "N/A"
        viewHolder.postRepliesCount.text = "${posts[position].replies?.size} replies" ?: "No replies"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = posts.count()
}