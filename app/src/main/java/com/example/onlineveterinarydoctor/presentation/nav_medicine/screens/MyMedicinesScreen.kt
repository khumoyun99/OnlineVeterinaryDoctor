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
            }

            override fun onCancelled(error : DatabaseError) {
                showToast(error.message)
            }
        })


    }

    private fun loadData() {
//        medicinesList = ArrayList()
//        val namesList = arrayListOf(
//            "Otoclean Ear Cleaner 18 x 5ml vials (Dogs & Cats)" ,
//            "Remend Corneal Repair Eye Gel 10 x 3ml (Dogs, Cats & Horses)" ,
//            "Remend Eye Drops 4 x 10ml (Dogs)" ,
//            "Surosolve Ear Cleaner 125ml - Dogs & Cats" ,
//            "Lubrithal Eye Gel 10g (Dogs & Cats)"
//
//
//        )
//        val descriptionList =
//            arrayListOf(
//                "Otoclean Ear Cleaner - bu ortiqcha mum va qoldiqlarni bo'shatish va olib tashlash uchun mo'ljallangan, qulay, ishlatish uchun qulay va kam tirnash xususiyati beruvchi quloqlarni tozalash vositasi." ,
//                "Remend Corneal gel itlar, mushuklar va otlardagi shox pardaning yuzaki yaralarini davolashda yordam sifatida foydalanish uchun ko'rsatiladi." ,
//                "Remend Dry Eye Lubricant Drops itlar uchun ko'zlar uchun uzoq muddatli namlik va moylash imkonini beradi va kuniga ikki marta foydalanilganda Quruq ko'z belgilarini yumshatishga yordam beradi." ,
//                "Surosolve quloq kanalini tozalaydi va namlaydi, shuningdek, sezilarli antibakterial va antifungal ta'sirga ega." ,
//                "Lubrithal Eye Gel - ko'zning yosh qatlamini namlash va saqlashga yordam beradigan karbomer moylash suyuqligi."
//            )
//        val priceList = arrayListOf(
//            182500 ,
//            867500 ,
//            683900 ,
//            189000 ,
//            104000
//        )
//        val doctorsNameList = arrayListOf(
//            "R.A.Ashurmatova" ,
//            "A.F.Ganiyeva" ,
//            "R.A.Ashurmatova" ,
//            "A.F.Ganiyeva" ,
//            "R.A.Ashurmatova"
//        )
//        val imgList = arrayListOf(
//            R.drawable.img_dogs_otoclean_pr ,
//            R.drawable.img_medicine_remand ,
//            R.drawable.img_medicine_eye_drops ,
//            R.drawable.img_medicine_surosolve ,
//            R.drawable.img_medicine_lubrithal ,
//        )
//
//        for (i in 0 until 5) {
//            medicinesList.add(
//                Medicines(
//                    i ,
//                    namesList[i] ,
//                    descriptionList[i] ,
//                    priceList[i] ,
//                    doctorsNameList[i] ,
//                    imgList[i]
//                )
//            )
//        }
//
    }

}