package com.example.onlineveterinarydoctor.presentation.nav_patient.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.ScreeenAnimalInfoBinding
import com.example.onlineveterinarydoctor.utils.scope
import com.squareup.picasso.Picasso

class AnimalInfoScreen:Fragment(R.layout.screeen_animal_info) {

    private val binding by viewBinding(ScreeenAnimalInfoBinding::bind)
    private val args : AnimalInfoScreenArgs by navArgs()


    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        Picasso.get().load(args.animal.image).into(ivAnimalsImage)
        etType.setText(args.animal.type)
        etName.setText(args.animal.name)
        etAge.setText(args.animal.age)
        etColor.setText(args.animal.color)
        etAnimalType.setText(args.animal.animalsType)
        etGender.setText(args.animal.gender)
        etAnimalWeight.setText(args.animal.weight)
        etAnimalWidthHeight.setText(args.animal.widthHeight)
        etAdditional.setText(args.animal.additionalInfo)

    }

}