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
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import java.lang.ref.Reference

class Signup : AppCompatActivity() {


    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtName: EditText
    private lateinit var btnSignup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDBRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        mAuth= FirebaseAuth.getInstance()
        edtEmail = findViewById(R.id.email)
        edtPassword = findViewById(R.id.pass)
        edtName = findViewById(R.id.name)
        btnSignup = findViewById(R.id.signup)

        btnSignup.setOnClickListener {
            val name =edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            signup(name,email, password)
        }

    }
    private fun signup(name:String,email:String,password:String)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                {
                    addUsertoDatabase(name,email,mAuth.currentUser?.uid!!)
                    //if, code for jumping to home activity
                    val intent=Intent(this@Signup,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else
                {
                   //else
                    Toast.makeText(this@Signup,"Some error occured",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUsertoDatabase(name:String,email: String,uid:String)
    {
        mDBRef=FirebaseDatabase.getInstance().getReference()
        mDBRef.child("user").child(uid).setValue(USER(name,email,uid))
    }

}



