package com.quizapp.tork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    private val POINTS = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val crctAns = intent.getIntExtra("correct",0)
        val totalQues = intent.getIntExtra("total",0)

        val point = crctAns * POINTS

        score.text = String.format("%d/%d",crctAns,totalQues)
        coins.text = point.toString()

        val database = FirebaseFirestore.getInstance()

        FirebaseAuth.getInstance().uid?.let {
            database.collection("users")
                .document(it)
                .update("coins",FieldValue.increment(point.toLong()))
        }
    }
}