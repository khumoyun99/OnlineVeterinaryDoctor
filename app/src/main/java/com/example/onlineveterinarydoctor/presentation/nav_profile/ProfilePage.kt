package com.example.onlineveterinarydoctor.presentation.nav_profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.PageProfileBinding
import com.example.onlineveterinarydoctor.presentation.nav_login.models.Account
import com.example.onlineveterinarydoctor.utils.gone
import com.example.onlineveterinarydoctor.utils.scope
import com.example.onlineveterinarydoctor.utils.showToast
import com.example.onlineveterinarydoctor.utils.visible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class ProfilePage:Fragment(R.layout.page_profile) {

    private val binding by viewBinding(PageProfileBinding::bind)
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var firebaseDatabase : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    private lateinit var account : Account

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)


        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("doctors")

        reference.addValueEventListener(object:ValueEventListener {
            override fun onDataChange(snapshot : DataSnapshot) {
                try {
                    val children = snapshot.children
                    for (child in children) {
                        val user = child.getValue(Account::class.java)
                        if (user?.uid == firebaseAuth.uid && user != null) {
                            account = user
                        }
                    }
                    Picasso.get().load(account.photoUrl).error(R.drawable.ic_profile_person)
                        .placeholder(R.drawable.ic_profile_person)
                        .into(imgProfileImage)
                    tvProfileName.text = account.displayName
                    etProfileName.setText(account.displayName)
                    etProfileEmail.setText(account.email)
                    etProfilePhoneNumber.setText(account.phoneNumber)
                    etProfileAddress.setText(account.address)

                } catch (e : Exception) {
                    showToast(e.message.toString())
                }
            }


            override fun onCancelled(error : DatabaseError) {
                showToast(error.message)
            }
        })

        imgEditPencil.setOnClickListener {
            imgEditPencil.gone()
            btnProfileSave.visible()

            etProfileName.isEnabled = true
            etProfilePhoneNumber.isEnabled = true
            etProfileAddress.isEnabled = true
        }

        btnProfileSave.setOnClickListener {
            imgEditPencil.visible()
            btnProfileSave.gone()

            etProfileName.isEnabled = false
            etProfilePhoneNumber.isEnabled = false
            etProfileAddress.isEnabled = false

            val name = etProfileName.text.toString()
            val phoneNumber = etProfilePhoneNumber.text.toString()
            val address = etProfileAddress.text.toString()

            reference.child(firebaseAuth.currentUser?.uid ?: "").setValue(
                Account(
                    uid = account.uid.toString() ,
                    displayName = name ,
                    phoneNumber = phoneNumber ,
                    email = account.email.toString() ,
                    photoUrl = account.photoUrl.toString() ,
                    address = address ,
                    myMedicine = account.myMedicine
                )
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast("Ma'lumotlaringiz o'zgartirildi")
                } else {
                    showToast("Ma'lumotlar o'zgartirishda xatolik!")
                }
            }
        }


    }

}