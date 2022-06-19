package com.example.onlineveterinarydoctor.presentation.nav_medicine.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.ScreenAddMedicineBinding
import com.example.onlineveterinarydoctor.presentation.nav_medicine.models.Medicines
import com.example.onlineveterinarydoctor.utils.scope
import com.example.onlineveterinarydoctor.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddMedicineScreen:Fragment(R.layout.screen_add_medicine) {

    private val binding by viewBinding(ScreenAddMedicineBinding::bind)
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var firebaseDatabase : FirebaseDatabase
    private lateinit var reference : DatabaseReference

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference =
            firebaseDatabase.getReference("doctors/${firebaseAuth.currentUser?.uid}/myMedicine")

        btnAddMedicine.setOnClickListener {
            val key = reference.push().key
            val name = etMedicineName.text.toString()
            val description = etMedicineDescription.text.toString()
            val price = etMedicinePrice.text.toString()

            if (name.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && price.isNotEmpty()) {
                val medicines = Medicines(
                    id = key ,
                    name = name ,
                    description = description ,
                    price = price.toInt() ,
                    doctorsName = firebaseAuth.currentUser?.displayName ,
                    imageUrl = ""
                )

                reference.child(key ?: "").setValue(medicines)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showToast("Dori muvaffaqiyatli qo'shildi")
                            etMedicineName.text?.clear()
                            etMedicineDescription.text?.clear()
                            etMedicinePrice.text?.clear()

                        } else {
                            showToast(it.exception?.message.toString())
                        }

                    }

            } else {
                showToast("Bo'sh kataklarni to'ldiring!")
            }


        }


    }
}