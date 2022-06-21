package com.example.onlineveterinarydoctor.presentation.nav_medicine.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.ScreenMyMedicinesBinding
import com.example.onlineveterinarydoctor.presentation.nav_medicine.adapters.MedicineRvAdapter
import com.example.onlineveterinarydoctor.presentation.nav_medicine.models.Medicines
import com.example.onlineveterinarydoctor.presentation.nav_patient.screens.models.MyAnimal
import com.example.onlineveterinarydoctor.utils.scope
import com.example.onlineveterinarydoctor.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MyMedicinesScreen:Fragment(R.layout.screen_my_medicines) {

    private val binding by viewBinding(ScreenMyMedicinesBinding::bind)
    private lateinit var medicineRvAdapter : MedicineRvAdapter
    private lateinit var medicinesList : ArrayList<Medicines>
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var firebaseDatabase : FirebaseDatabase
    private lateinit var reference : DatabaseReference

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference =
            firebaseDatabase.getReference("doctors/${firebaseAuth.currentUser?.uid}/myMedicine")

        medicinesList = ArrayList()
        medicineRvAdapter = MedicineRvAdapter(object:MedicineRvAdapter.OnItemTouchClickListener {
            override fun onItem(medicines : Medicines) {

            }
        })

        reference.addValueEventListener(object:ValueEventListener {
            override fun onDataChange(snapshot : DataSnapshot) {
                try {
                    medicinesList.clear()
                    val children = snapshot.children
                    for (child in children) {
                        val myMedicine = child.getValue(Medicines::class.java)
                        if (myMedicine != null) {
                            medicinesList.add(myMedicine)
                        }
                    }

                    medicineRvAdapter.mySubmitList(medicinesList)
                    rvMedicine.apply {
                        setHasFixedSize(true)
                        adapter = medicineRvAdapter
                    }
                    medicineRvAdapter.notifyDataSetChanged()
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

