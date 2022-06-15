package com.example.onlineveterinarydoctor.presentation.nav_medicine.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.onlineveterinarydoctor.presentation.nav_medicine.screens.MedicineOrderScreen
import com.example.onlineveterinarydoctor.presentation.nav_medicine.screens.MyMedicinesScreen

class MedicineVpAdapter(activity : FragmentActivity):FragmentStateAdapter(activity) {

    override fun getItemCount() : Int {
        return 2
    }

    override fun createFragment(position : Int) : Fragment {
        return when (position) {
            0 -> {
                MyMedicinesScreen()
            }
            else -> {
                MedicineOrderScreen()
            }
        }
    }
}