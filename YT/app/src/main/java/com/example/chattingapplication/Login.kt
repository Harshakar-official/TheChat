package com.example.chattingapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        mAuth= FirebaseAuth.getInstance()
        edtEmail=findViewById(R.id.email)
        edtPassword=findViewById(R.id.pass)
        btnLogin=findViewById(R.id.login)
        btnSignup=findViewById(R.id.signup)

        // Check if there is a current user
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            // If there is a current user, go to the main screen
            val intent = Intent(this@Login, MainActivity::class.java)
            finish()
            startActivity(intent)
            return
        }

        btnSignup.setOnClickListener{
            val intent=Intent(this,Signup::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener{
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            login(email,password);
        }
    }
    private fun login(email:String,password:String)
    {
        //login for user
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                {
                   //if part
                    val intent=Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else
                {
                   //else part
                    Toast.makeText(this@Login,"USER NOT EXIST",Toast.LENGTH_SHORT).show()
                }
            }
    }

}