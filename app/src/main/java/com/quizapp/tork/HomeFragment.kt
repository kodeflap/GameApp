package com.quizapp.tork

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.quizapp.tork.adapter.CategoryAdapter
import com.quizapp.tork.model.Category

class HomeFragment : Fragment() {

    var database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val database = FirebaseFirestore.getInstance()
        val recycler = view.findViewById<RecyclerView>(R.id.cat_items)
        val data = ArrayList<Category>()
        val adapter = CategoryAdapter(data)

        database.collection("categories")
            .addSnapshotListener(EventListener { snapshot, error ->
                data.clear()
                snapshot?.documents?.forEach{ documentSnapshot ->
                    var category: Category? = documentSnapshot.toObject(Category::class.java)
                    category?.cat_id = documentSnapshot.id
                    data.add(category!!)
                }
                adapter.notifyDataSetChanged();
            })

        recycler.layoutManager = GridLayoutManager(context,2)
        recycler.adapter = adapter
        return  view
    }
}