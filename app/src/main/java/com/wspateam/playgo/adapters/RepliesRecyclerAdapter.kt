package com.wspateam.playgo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wspateam.playgo.R
import com.wspateam.playgo.models.Reply

class RepliesRecyclerAdapter(private val replies: MutableList<Reply>): RecyclerView.Adapter<RepliesRecyclerAdapter.ViewHolder>()
{
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var authorName = view.findViewById<TextView>(R.id.authorReplyItem)
        var body = view.findViewById<TextView>(R.id.bodyReplyItem)
        var date = view.findViewById<TextView>(R.id.dateReplyItem)
        var likes = view.findViewById<TextView>(R.id.likesReplyItem)
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder
    {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.forum_message_item, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
    {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.authorName.text = replies[position].author
        viewHolder.body.text = replies[position].body
        viewHolder.date.text = replies[position].date
        //TODO add replies
        viewHolder.likes.text = "0"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = replies.count()
}