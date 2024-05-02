package com.example.chattingapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User
import org.checkerframework.checker.units.qual.Current

class MainActivity : AppCompatActivity() {
    private lateinit var userRecylerview: RecyclerView
    private lateinit var userList:ArrayList<USER>
    private lateinit var adapter: user_adapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        mAuth=FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()
        userList= ArrayList()
        adapter=user_adapter(this,userList)
        userRecylerview=findViewById(R.id.userRecylerview)
        userRecylerview.layoutManager=LinearLayoutManager(this)
        userRecylerview.adapter=adapter
        mDbRef.child("user").addValueEventListener(object:ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children )
                {
                    val currentUser=postSnapshot.getValue(USER::class.java)
                    userList.add(currentUser!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.logout)
        {
            mAuth.signOut()
            val intent=Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}