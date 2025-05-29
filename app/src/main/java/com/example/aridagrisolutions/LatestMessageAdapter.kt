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


class LatestMessageAdapter(private var messageList: ArrayList<MessageMC>) : RecyclerView.Adapter<LatestMessageAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView =itemView.findViewById(R.id.userName)
        var recentMessage: TextView =itemView.findViewById(R.id.recentMessage)
        var userImg: ImageView = itemView.findViewById(R.id.userImg)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.latest_user_message_list, parent, false)
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

        val ref = FirebaseDatabase.getInstance().getReference("Expert/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var expert = snapshot.getValue(ExpertMC::class.java)
                holder.userName.text = expert?.name
                Picasso.get().load(expert?.imageLink).into(holder.userImg)

                holder.itemView.setOnClickListener{
                    val intent= Intent(holder.userName.context, MessageActivity::class.java)
                    intent.putExtra("name", expert?.name)
                    intent.putExtra("uid", expert?.authId)
                    intent.putExtra("img", expert?.imageLink)
                    holder.userName.context.startActivity(intent)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    override fun getItemCount(): Int {
        return messageList.size
    }
}
