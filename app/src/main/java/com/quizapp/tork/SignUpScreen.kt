package com.quizapp.tork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpScreen : AppCompatActivity() {

    lateinit var email : String
    lateinit var password : String
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        sign_in.setOnClickListener{

            authLogin()
        }

    }

    override fun onStart() {
        super.onStart()
        //val user : FirebaseUser? =firebaseAuth
    }

    private fun authLogin() {

        email = s_email.text.toString().trim()
        password = s_psw.text.toString().trim()

        // create user
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "created account successfully .....!", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, task.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
    }
}