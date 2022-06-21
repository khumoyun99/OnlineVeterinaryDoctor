package com.example.onlineveterinarydoctor.presentation.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.presentation.nav_patient.adapter.DoctorUserRvMessageAdapter
import com.example.onlineveterinarydoctor.presentation.nav_patient.models.Messages
import com.example.onlineveterinarydoctor.presentation.nav_patient.models.Patient
import com.example.onlineveterinarydoctor.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity:AppCompatActivity(R.layout.activity_chat) {

    private lateinit var doctorUserRvMessageAdapter : DoctorUserRvMessageAdapter
    private lateinit var allMessageList : ArrayList<Messages>
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var firebaseDatabase : FirebaseDatabase
    private lateinit var referenceFrom : DatabaseReference
    private lateinit var referenceTo : DatabaseReference

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        window.statusBarColor = Color.parseColor("#5726E4")
        val patient = intent.getSerializableExtra("patient") as Patient
        val rvChat = findViewById<RecyclerView>(R.id.rvChat)
        val etChat = findViewById<EditText>(R.id.etChat)
        val toolbarChat = findViewById<Toolbar>(R.id.toolbar_chat)
        val buttonChatSend = findViewById<Button>(R.id.button_chat_send)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        referenceFrom =
            firebaseDatabase.getReference("doctors/${firebaseAuth.currentUser?.uid}/allMessages/${patient.id}")
        referenceTo =
            firebaseDatabase.getReference("users/${patient.id}/allMessages/${firebaseAuth.currentUser?.uid}")


        toolbarChat.title = patient.name
        toolbarChat.setNavigationOnClickListener {
            onBackPressed()
        }


        buttonChatSend.setOnClickListener {
            val myMessage = etChat.text.toString()
            val keyFrom = referenceFrom.push().key
            val keyTo = referenceTo.push().key
            if (myMessage.isNotEmpty()) {
                val message = Messages(
                    id = keyFrom ,
                    message = myMessage ,
                    from = "doctor${firebaseAuth.currentUser?.uid}" ,
                    to = "user${patient.id}" ,
                    date = System.currentTimeMillis()
                )
                referenceFrom.child(keyFrom ?: "").setValue(message)
                referenceTo.child(keyTo ?: "").setValue(message)
                etChat.text.clear()

            } else {
                showToast("Maydonni to'ldiring")
            }
        }


        allMessageList = ArrayList()
        doctorUserRvMessageAdapter = DoctorUserRvMessageAdapter()
        rvChat.apply {
            setHasFixedSize(true)
            adapter = doctorUserRvMessageAdapter
        }

        referenceFrom.addValueEventListener(object:ValueEventListener {
            override fun onDataChange(snapshot : DataSnapshot) {
                try {
                    allMessageList.clear()
                    val children = snapshot.children
                    for (child in children) {
                        val message = child.getValue(Messages::class.java)
                        if (message != null) {
                            allMessageList.add(message)
                        }
                    }
                    doctorUserRvMessageAdapter.mySubmitList(
                        allMessageList ,
                        "doctor${firebaseAuth.currentUser?.uid}"
                    )
                    doctorUserRvMessageAdapter.notifyDataSetChanged()
                    rvChat.scrollToPosition(doctorUserRvMessageAdapter.itemCount - 1)

                } catch (e : Exception) {
                    showToast(e.message.toString())
                }
            }

            override fun onCancelled(error : DatabaseError) {
                showToast(error.message)
            }
        })


    }

}