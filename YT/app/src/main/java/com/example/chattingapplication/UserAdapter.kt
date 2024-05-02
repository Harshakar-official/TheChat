package com.example.chattingapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth

class user_adapter(val context:Context,val userList: ArrayList<USER>):
    RecyclerView.Adapter<user_adapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder
    {
        val view:View=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int
    {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int)
    {
        val currentuser=userList[position]
        holder.textname.text=currentuser.name
        holder.itemView.setOnClickListener{
            val intent=Intent(context,ChatActivity::class.java)
            intent.putExtra("name",currentuser.name)
            intent.putExtra("uid",currentuser.uid)
            context.startActivity(intent)
        }
    }
    class UserViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        val textname=itemView.findViewById<TextView>(R.id.txt_name)
    }
}