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
import com.example.onlineveterinarydoctor.presentation.nav_patient.adapter.PatientRvAdapter
import com.example.onlineveterinarydoctor.presentation.nav_patient.models.Animal
import com.example.onlineveterinarydoctor.presentation.nav_patient.models.Patient
import com.example.onlineveterinarydoctor.utils.scope
import com.google.android.material.bottomnavigation.BottomNavigationView

class PatientPage:Fragment(R.layout.page_patient) {

    private val binding by viewBinding(PagePatientBinding::bind)
    private lateinit var patientRvAdapter : PatientRvAdapter
    private lateinit var patientList : ArrayList<Patient>

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        loadData()
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
        patientRvAdapter.mySubmitList(patientList)
        rvPatient.setHasFixedSize(true)
        rvPatient.adapter = patientRvAdapter

    }

    override fun onResume() {
        super.onResume()
        val bottomNav =
            findRootView(requireActivity()).findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.VISIBLE
    }

    private fun loadData() {
        patientList = ArrayList()
        val animalList : ArrayList<Animal> = ArrayList()

        for (i in 0 until 20) {
            animalList.add(
                Animal(
                    id = i ,
                    type = "Dog" ,
                    name = "Tuzik0" ,
                    age = "2" ,
                    color = "#FFFFFF" ,
                    animalsType = "Dolmatin" ,
                    gender = "erkak" ,
                    weight = "6 kg" ,
                    widthHeight = "0.4/0.6 m" ,
                    image = R.drawable.img_dog_ovcharka ,
                    additionalInfo = "M'alumot yo'q"
                )
            )

        }


        for (i in 0 until 5) {
            patientList.add(
                Patient(
                    id = i ,
                    name = "Bozorova Shohlo" ,
                    email = "shaxlo@mail.ru" ,
                    img = R.drawable.img ,
                    phoneNumber = "+998912345678" ,
                    address = "Toshkent" ,
                    animalsList = animalList
                )
            )
        }

    }
}