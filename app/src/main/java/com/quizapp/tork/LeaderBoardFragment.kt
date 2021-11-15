package com.quizapp.tork

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.quizapp.tork.adapter.LeaderBoardAdapter
import com.quizapp.tork.model.User

class LeaderBoardFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_leader_board, container, false)
        val database = FirebaseFirestore.getInstance()
        val data = ArrayList<User>()
        val adapter = LeaderBoardAdapter(data)
        val recycler = view.findViewById<RecyclerView>(R.id.rec_leader)

        database.collection("users")
            .orderBy("coins",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for(snapshot : DocumentSnapshot in querySnapshot)
                {
                    val user = snapshot.toObject(User::class.java)
                    if (user != null) {
                        data.add(user)
                    }
                }

                adapter.notifyDataSetChanged()
            }

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        return view
    }
}