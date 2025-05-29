package com.example.aridagrisolutions

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
class ShowUserMessageAdapter(private var messageList: ArrayList<MessageMC>) :
    RecyclerView.Adapter<ShowUserMessageAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView =itemView.findViewById(R.id.userName)
        var recentMessage: TextView =itemView.findViewById(R.id.recentMessage)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowUserMessageAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.latest_message_list_by_expert, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val chatMessage: MessageMC = messageList[position]
        holder.recentMessage.text= chatMessage.message

        val chatPartnerId:String?
        if(chatMessage.senderId == FirebaseAuth.getInstance()?.uid)
        {
            chatPartnerId = chatMessage.receiverId
        }
        else
        {
            chatPartnerId = chatMessage.senderId
        }

        val ref = FirebaseDatabase.getInstance().getReference("USERS/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var user = snapshot.getValue(userMC::class.java)
                holder.userName.text = user?.name

                holder.itemView.setOnClickListener {
                    val intent = Intent(holder.userName.context, MessageActivity::class.java)
                    intent.putExtra("name", user?.name)
                    intent.putExtra("uid", user?.authId)
                    holder.userName.context.startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    override fun getItemCount(): Int {
        return messageList.size
    }
}





