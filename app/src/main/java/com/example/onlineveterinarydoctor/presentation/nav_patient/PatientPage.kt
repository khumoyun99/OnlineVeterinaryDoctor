package com.example.onlineveterinarydoctor.presentation.nav_patient

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.internal.findRootView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.PagePatientBinding
import com.example.onlineveterinarydoctor.presentation.nav_login.models.Account
import com.example.onlineveterinarydoctor.presentation.nav_patient.adapter.PatientRvAdapter
import com.example.onlineveterinarydoctor.presentation.nav_patient.models.Patient
import com.example.onlineveterinarydoctor.utils.scope
import com.google.android.material.bottomnavigation.BottomNavigationView
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

//        loadData()
        patientRvAdapter = PatientRvAdapter(object:PatientRvAdapter.OnPatientItemClickListener {
            override fun onPatClick(patient : Patient) {
                val bottomNav =
                    findRootView(requireActivity()).findViewById<BottomNavigationView>(R.id.bottom_navigation)
                bottomNav.visibility = View.GONE

                findNavController().navigate(
                    PatientPageDirections.actionPatientPageToPatientAnimalsScreen(
                        patient
                    )
                )

            }

            override fun onMessageClick(patient : Patient) {
                val bottomNav =
                    findRootView(requireActivity()).findViewById<BottomNavigationView>(R.id.bottom_navigation)
                bottomNav.visibility = View.GONE

                findNavController().navigate(
                    PatientPageDirections.actionPatientPageToMessagesPatientScreen(
                        patient
                    )
                )

                val toolbar = findRootView(requireActivity()).findViewById<Toolbar>(R.id.toolbar)
                toolbar.title = patient.name
            }
        })

        reference.addValueEventListener(object:ValueEventListener {
            override fun onDataChange(snapshot : DataSnapshot) {
                val children = snapshot.children
                for (child in children) {
                    val user = child.getValue(Account::class.java)
                    patientList.add(
                        Patient(
                            id = user?.uid ?: "" ,
                            name = user?.displayName ?: "" ,
                            email = user?.email ?: "" ,
                            img = user?.email ?: "" ,
                            phoneNumber = user?.phoneNumber ?: "" ,
                            address = ""
                        )
                    )
                }
                patientRvAdapter.mySubmitList(patientList)
                rvPatient.setHasFixedSize(true)
                rvPatient.adapter = patientRvAdapter
                patientRvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error : DatabaseError) {

            }
        })


    }

    override fun onResume() {
        super.onResume()
        val bottomNav =
            findRootView(requireActivity()).findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.VISIBLE
    }
}