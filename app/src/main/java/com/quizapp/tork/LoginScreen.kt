package com.quizapp.tork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginScreen : AppCompatActivity() {
    lateinit var email : String
    lateinit var password : String
    var auth = FirebaseAuth.getInstance()
    var database = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        l_login_in.setOnClickListener{

            authLogin()
        }
        l_sign_in.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,SignUpScreen::class.java)
            startActivity(intent)
            finish()
        })

    }
    private fun authLogin() {

        email = s_email.text.toString().trim()
        password = s_psw.text.toString().trim()

        //User user = new User(name,email,password,refCode)


        // create user
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this, OnCompleteListener { task ->
                if(task.isSuccessful){

                    var uid = task.result?.user?.uid

                    //   database.collection("users").document(uid!!).set(user).addOnCompleteListener(onCompleteListener)


                    Toast.makeText(this, "Successfully Registered...", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,HomeScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, task.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                }
            })
    }
}