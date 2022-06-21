package com.example.onlineveterinarydoctor.presentation.nav_patient

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.PagePatientBinding
import com.example.onlineveterinarydoctor.presentation.activity.ChatActivity
import com.example.onlineveterinarydoctor.presentation.nav_patient.adapter.PatientRvAdapter
import com.example.onlineveterinarydoctor.presentation.nav_patient.models.Animal
import com.example.onlineveterinarydoctor.presentation.nav_patient.models.Patient
import com.example.onlineveterinarydoctor.presentation.nav_patient.screens.models.MyAnimal
import com.example.onlineveterinarydoctor.presentation.nav_patient.screens.models.UserAccount
import com.example.onlineveterinarydoctor.utils.scope
import com.example.onlineveterinarydoctor.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PatientPage:Fragment(R.layout.page_patient) {

    private val binding by viewBinding(PagePatientBinding::bind)
    private lateinit var patientRvAdapter : PatientRvAdapter
    private lateinit var patientList : ArrayList<Patient>
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var firebaseDatabase : FirebaseDatabase
    private lateinit var reference : DatabaseReference

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        patientList = ArrayList()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users")


        patientRvAdapter = PatientRvAdapter(object:PatientRvAdapter.OnPatientItemClickListener {
            override fun onPatClick(patient : Patient) {

                findNavController().navigate(
                    PatientPageDirections.actionPatientPageToPatientAnimalsScreen(
                        patient
                    )
                )

            }

            override fun onMessageClick(patient : Patient) {
                val intent = Intent(requireActivity() , ChatActivity::class.java)
                intent.putExtra("patient",patient)
                startActivity(intent)
            }
        })

        try {
            reference.addValueEventListener(object:ValueEventListener {
                override fun onDataChange(snapshot : DataSnapshot) {
                    try {
                        patientList.clear()
                        val children = snapshot.children
                        for (child in children) {
                            val user = child.getValue(UserAccount::class.java)

                            val animalsList : ArrayList<Animal> = ArrayList()
                            reference.child("${user?.uid}/myAnimals")
                                .addValueEventListener(object:ValueEventListener {
                                    override fun onDataChange(snapshot : DataSnapshot) {
                                        try {
                                            val childrenAnimals = snapshot.children
                                            for (childrenAnimal in childrenAnimals) {
                                                val myAnimal =
                                                    childrenAnimal.getValue(MyAnimal::class.java)
                                                if (myAnimal != null) {
                                                    animalsList.add(
                                                        Animal(
                                                            id = myAnimal.uid.toString() ,
                                                            type = myAnimal.animalType.toString() ,
                                                            name = myAnimal.name.toString() ,
                                                            age = myAnimal.age.toString() ,
                                                            color = myAnimal.color.toString() ,
                                                            animalsType = myAnimal.animalType.toString() ,
                                                            gender = myAnimal.gender.toString() ,
                                                            weight = myAnimal.weight.toString() ,
                                                            widthHeight = myAnimal.widHeight.toString() ,
                                                            image = myAnimal.photoUrl.toString() ,
                                                            additionalInfo = myAnimal.additional.toString()
                                                        )
                                                    )
                                                }
                                            }
                                        } catch (e : Exception) {
                                            showToast("three ${e.message.toString()}")
                                        }

                                    }

                                    override fun onCancelled(error : DatabaseError) {
                                        showToast(error.message)
                                    }
                                })

                            patientList.add(
                                Patient(
                                    id = user?.uid ?: "" ,
                                    name = user?.displayName ?: "" ,
                                    email = user?.email ?: "" ,
                                    img = user?.photoUrl ?: "" ,
                                    phoneNumber = user?.phoneNumber ?: "" ,
                                    address = user?.address ?: "" ,
                                    animalsList = animalsList
                                )
                            )
                        }

                        patientRvAdapter.mySubmitList(patientList)
                        rvPatient.setHasFixedSize(true)
                        rvPatient.adapter = patientRvAdapter
                        patientRvAdapter.notifyDataSetChanged()

                    } catch (e : Exception) {
                        showToast("two ${e.message.toString()}")
                    }

                }

                override fun onCancelled(error : DatabaseError) {
                    showToast(error.message)

                }
            })
        } catch (e : Exception) {
            showToast("one ${e.message.toString()}")
        }


    }
}