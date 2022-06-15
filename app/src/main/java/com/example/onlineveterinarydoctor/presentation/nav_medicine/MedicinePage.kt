package com.example.onlineveterinarydoctor.presentation.nav_medicine

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.PageMedicineBinding
import com.example.onlineveterinarydoctor.presentation.nav_medicine.adapters.MedicineVpAdapter
import com.example.onlineveterinarydoctor.utils.scope
import com.google.android.material.tabs.TabLayoutMediator

class MedicinePage:Fragment(R.layout.page_medicine) {

    private val binding by viewBinding(PageMedicineBinding::bind)
    private lateinit var medicineVpAdapter : MedicineVpAdapter
    private val tabList = arrayListOf("My medicine" , "Orders")

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)
        setHasOptionsMenu(true)

        medicineVpAdapter = MedicineVpAdapter(requireActivity())
        vpMedicine.adapter = medicineVpAdapter

        TabLayoutMediator(tabLayout , vpMedicine) { tab , pos ->
            tab.text = tabList[pos]
        }.attach()

    }

    override fun onCreateOptionsMenu(menu : Menu , inflater : MenuInflater) {
        super.onCreateOptionsMenu(menu , inflater)
        inflater.inflate(R.menu.medicine_menu , menu)
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        when (item.itemId) {
            R.id.add_medicine -> {
                findNavController().navigate(MedicinePageDirections.actionMedicinePageToAddMedicineScreen())
            }
        }
        return super.onOptionsItemSelected(item)

    }


}