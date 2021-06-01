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

class PostsRecyclerAdapter(private val posts: MutableList<Post>): RecyclerView.Adapter<PostsRecyclerAdapter.ViewHolder>()
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
        var postAuthor: TextView = view.findViewById<TextView>(R.id.authorReplyItem)
        var postBody: String = ""
        var postDate: TextView = view.findViewById<TextView>(R.id.dateReplyItem)
        var postRepliesCount: TextView = view.findViewById(R.id.repliesCountForumPostItem)

        init
        {
            rootLayout.setOnClickListener {
                Toast.makeText(view.context, "Clicked on post.", Toast.LENGTH_SHORT).show()
                val post = Post()
                post.uid = postUid
                post.author = postAuthor.text.toString()
                post.body = postBody
                post.date = postDate.text.toString()
                post.title = postTitle.text.toString()
                val action = RoomsFragmentDirections.actionRoomsFragmentToForumPostFragment(post)
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
        viewHolder.postRepliesCount.text = "${posts[position].repliesCount} replies" ?: "No replies"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = posts.count()
}