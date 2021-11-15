package com.quizapp.tork.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quizapp.tork.R
import com.quizapp.tork.model.User

class LeaderBoardAdapter(private val user: List<User>):RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder>() {

    class LeaderBoardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name = view.findViewById<TextView>(R.id.names)
        val points = view.findViewById<TextView>(R.id.point)
        val index = view.findViewById<TextView>(R.id.indexs)
        val img = view.findViewById<ImageView>(R.id.profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderBoardViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.leader_board_item,parent,false)
        return LeaderBoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeaderBoardViewHolder, position: Int) {
        val context = holder.itemView.context
        val users = user[position]
        holder.name.text = users.name
        holder.points.text = users.coins.toString()
        holder.index.text = String.format("#%d",position+1)
        Glide.with(context)
            .load(users.profile)
            .into(holder.img)
    }

    override fun getItemCount(): Int {
        return user.size
    }
}