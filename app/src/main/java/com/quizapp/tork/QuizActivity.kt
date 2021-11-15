package com.quizapp.tork

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.quizapp.tork.model.Question
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {

    private var index = 0
    private var crct = 0
    private var time:CountDownTimer? = null
    private var question  = ArrayList<Question>()
    private var quest = Question()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val catId = intent.getStringExtra("catId")
        val database = FirebaseFirestore.getInstance()
        val random = Random.nextInt(12)


      database.collection("categories")
            .document(catId!!)
            .collection("Questions")
            .whereGreaterThanOrEqualTo("index",random)
            .orderBy("index")
            .limit(5)
            .get().addOnSuccessListener { querySnapshot ->
              if(querySnapshot.documents.size < 5)
              {
                  database.collection("categories")
                      .document(catId)
                      .collection("Questions")
                      .whereLessThanOrEqualTo("index",random)
                      .orderBy("index")
                      .limit(5)
                      .get().addOnSuccessListener { querySnapshot ->
                          for(snapshot : DocumentSnapshot in querySnapshot)
                              run {
                                  val questions: Question =
                                      snapshot.toObject(Question::class.java)!!
                                  question.add(questions)
                              }
                      }
              }
              else
              {
                  for(snapshot : DocumentSnapshot in querySnapshot)
                      run {
                          val q:Question = snapshot.toObject(Question::class.java)!!
                          question.add(q)

                      }
              }
          }

       /** database.collection("categories")
            .addSnapshotListener{ snapshot, _ ->
                data.clear()
                snapshot?.documents?.forEach{ documentSnapshot ->
                    val category: Category? = documentSnapshot.toObject(Category::class.java)
                    category?.cat_id = documentSnapshot.id
                    data.add(category!!)
                }
                adapter.notifyDataSetChanged()
            }**/

        timerClock()
        setQuestion()
    }

    private fun setQuestion()
    {
        time?.cancel()
        time?.start()

        if(index < question.size)
        {
            ques_counter.text = String.format("%d/%d",(index+1),question.size)
            quest = question[index]
            ques.text = quest.ques
            op_1.text = quest.op1
            op_2.text = quest.op2
            op_3.text = quest.op3
            op_4.text = quest.op4
            progressBar.progress = index
            progressBar.max = question.size
        }
    }

    private fun checkAnswer(textView: TextView){

        val selectedAns = textView.text.toString()
        if(selectedAns == quest.ans)
        {
            crct++
            textView.background = ContextCompat.getDrawable(this,R.drawable.correct_option)
        }
        else
        {
            showAns()
            textView.background =ContextCompat.getDrawable(this,R.drawable.wrong_option)
        }

    }
    private fun reset()
    {
        op_1.background =ContextCompat.getDrawable(this,R.drawable.edit_text)
        op_2.background =ContextCompat.getDrawable(this,R.drawable.edit_text)
        op_3.background =ContextCompat.getDrawable(this,R.drawable.edit_text)
        op_4.background =ContextCompat.getDrawable(this,R.drawable.edit_text)

    }

    private fun timerClock()
    {
        time = object: CountDownTimer(30000,1000)
        {
            override fun onTick(millisUntilFinished: Long) {

                timer.text = (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {

            }

        }


    }

    private fun showAns()
    {
        when {
            quest.ans.equals(op_1.text.toString()) -> op_1.background = ContextCompat.getDrawable(this,R.drawable.correct_option)
            quest.ans.equals(op_2.text.toString()) -> op_2.background = ContextCompat.getDrawable(this,R.drawable.correct_option)
            quest.ans.equals(op_3.text.toString()) -> op_3.background = ContextCompat.getDrawable(this,R.drawable.correct_option)
            quest.ans.equals(op_4.text.toString()) -> op_4.background = ContextCompat.getDrawable(this,R.drawable.correct_option)
        }
    }

    fun onClick(view: View) {
        when(view.id)
        {
            R.id.op_1 ,R.id.op_2 ,R.id.op_3 ,R.id.op_4 -> {

                time?.cancel()
                val selected:TextView = view as TextView
                checkAnswer(selected)
                Toast.makeText(this, "Option Selected", Toast.LENGTH_LONG).show()
            }

            R.id.next -> {

                reset()
                if(index <= question.size) {
                    index++
                    setQuestion()
                }
                else
                {
                    val intent = Intent(this@QuizActivity,ResultActivity::class.java)
                    intent.putExtra("correct",crct)
                    intent.putExtra("total",question.size)
                    startActivity(intent)
                    finish()
                    //Toast.makeText(this,"Quiz Finished",Toast.LENGTH_LONG).show()
                }
            }
            R.id.quiz -> { }
        }
    }
}