package com.quizapp.tork

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_screen.*

class LoginScreen : AppCompatActivity() {
    private lateinit var email : String
    private lateinit var password : String
    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        if(auth.currentUser != null)
        {
            val intent = Intent(this@LoginScreen, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }

        l_login_in.setOnClickListener{

            authLogin()
        }
        register.setOnClickListener{
            val intent = Intent(this,SignUpScreen::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun authLogin() {

        email = l_email.text.toString().trim()
        password = l_psw.text.toString().trim()

        // create user
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){

                    task.result?.user?.uid

                   // Toast.makeText(this, "Logging In...", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,HomeScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, task.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
    }
}