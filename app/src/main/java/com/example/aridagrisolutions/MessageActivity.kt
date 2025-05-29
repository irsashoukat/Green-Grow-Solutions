package com.example.aridagrisolutions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MessageActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: Button
    private lateinit var messageAdapter:MessageAdapter
    private lateinit var messageList:ArrayList<MessageMC>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom:String?=null
    var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)


        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val image = intent.getStringExtra("img")

        supportActionBar?.title=name

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef= FirebaseDatabase.getInstance().getReference()
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox=findViewById(R.id.messageBox)
        sendButton=findViewById(R.id.sendMessage)
        messageList=ArrayList()
        messageAdapter= MessageAdapter(this,messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter


        //logic for adding data to recyclerView
        mDbRef.child("Chats").child(senderRoom!!).child("Messages")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(MessageMC::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()

                    chatRecyclerView.scrollToPosition(messageAdapter.itemCount-1)

                }

                override fun onCancelled(error: DatabaseError) {
                }

            })


        //adding messages to database
        sendButton.setOnClickListener{
            var message = messageBox.text.toString()

            val messageObject = MessageMC(message,senderUid, receiverUid, System.currentTimeMillis()/1000 )

            mDbRef.child("Chats").child(senderRoom!!).child("Messages").push()
                    .setValue(messageObject).addOnSuccessListener {

                        mDbRef.child("Chats").child(receiverRoom!!).child("Messages").push()
                                .setValue(messageObject)
                    }
            messageBox.setText("")

            val latestMessageRef = FirebaseDatabase.getInstance()
                    .getReference("latest_messages/$senderUid/$receiverUid")
            latestMessageRef.setValue(messageObject)

            val latestMessageRefTo = FirebaseDatabase.getInstance()
                    .getReference("latest_messages/$receiverUid/$senderUid")
            latestMessageRefTo.setValue(messageObject)

        }
    }
}