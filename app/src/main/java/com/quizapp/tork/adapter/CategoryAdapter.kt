package com.quizapp.tork.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quizapp.tork.R
import com.quizapp.tork.model.Category


class CategoryAdapter(private val catList: List<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val img : ImageView = view.findViewById(R.id.img)
        val cate : TextView = view.findViewById(R.id.category)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.home_category,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val category = catList[position]
        Glide.with(holder.itemView.context)
            .load(category.image)
            .into(holder.img)
        holder?.cate?.text = category.cat_title
    }

    override fun getItemCount(): Int {

        return catList.size
    }

}