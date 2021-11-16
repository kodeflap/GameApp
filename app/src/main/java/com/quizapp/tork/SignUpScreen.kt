package com.quizapp.tork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.quizapp.tork.model.User
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpScreen : AppCompatActivity() {

    lateinit var email : String
    lateinit var password : String
    lateinit var name : String
    lateinit var refCode : String
    var auth = FirebaseAuth.getInstance()
    var database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_up)

        sign_in.setOnClickListener{

            createUser()
        }

        login_in.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,LoginScreen::class.java)
            startActivity(intent)
            finish()
        })

    }

    override fun onStart() {
        super.onStart()
        //val user : FirebaseUser? =firebaseAuth
    }

    private fun createUser() {

        email = s_email.text.toString().trim()
        password = s_psw.text.toString().trim()
        name = s_name.text.toString().trim()
        refCode = ref_code.text.toString().trim()

        val user = User(name,email,password,refCode)


        // create user
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this, OnCompleteListener { task ->
                if(task.isSuccessful){

                    val uid = task.result?.user?.uid

                 database.collection("users")
                     .document(uid!!)
                     .set(user)
                     .addOnCompleteListener(OnCompleteListener { task ->

                         if (task.isSuccessful){

                             Toast.makeText(this, "Successfully Registered...", Toast.LENGTH_SHORT).show()
                             val intent = Intent(this,HomeScreen::class.java)
                             //intent.putExtra("uname",name)
                             startActivity(intent)
                             finish()
                         }
                         else
                         {
                             Toast.makeText(this, task.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                         }
                     })

                }
                else{
                    Toast.makeText(this, task.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                }
            })
    }
}