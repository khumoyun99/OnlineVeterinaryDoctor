package com.example.onlineveterinarydoctor.presentation.nav_patient.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.ScreenPatientAnimalsBinding
import com.example.onlineveterinarydoctor.presentation.nav_patient.adapter.AnimalsRvAdapter
import com.example.onlineveterinarydoctor.presentation.nav_patient.models.Animal
import com.example.onlineveterinarydoctor.utils.scope

class PatientAnimalsScreen:Fragment(R.layout.screen_patient_animals) {

    private val binding by viewBinding(ScreenPatientAnimalsBinding::bind)
    private lateinit var animalsRvAdapter : AnimalsRvAdapter
    private val args : PatientAnimalsScreenArgs by navArgs()

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        animalsRvAdapter = AnimalsRvAdapter(object:AnimalsRvAdapter.OnAnimalsClickListener {
            override fun onItemClick(animal : Animal) {
                findNavController().navigate(
                    PatientAnimalsScreenDirections.actionPatientAnimalsScreenToAnimalInfoScreeen(
                        animal
                    )
                )
            }
        })
        animalsRvAdapter.mySubmitList(args.patient.animalsList!!)
        val dividerItemDecoration = DividerItemDecoration(requireContext(),RecyclerView.VERTICAL)
        rvAnimals.addItemDecoration(dividerItemDecoration)
        rvAnimals.setHasFixedSize(true)
        rvAnimals.adapter = animalsRvAdapter
    }
}