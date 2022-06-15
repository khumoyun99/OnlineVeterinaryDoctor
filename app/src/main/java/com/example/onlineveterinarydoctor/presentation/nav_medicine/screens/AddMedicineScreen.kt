package com.example.onlineveterinarydoctor.presentation.nav_medicine.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.ScreenAddMedicineBinding
import com.example.onlineveterinarydoctor.utils.scope

class AddMedicineScreen:Fragment(R.layout.screen_add_medicine) {

    private val binding by viewBinding(ScreenAddMedicineBinding::bind)

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)
    }
}