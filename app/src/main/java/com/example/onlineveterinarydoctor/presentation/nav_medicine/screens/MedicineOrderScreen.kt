package com.example.onlineveterinarydoctor.presentation.nav_medicine.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.ScreenMedicineOrderesBinding
import com.example.onlineveterinarydoctor.presentation.nav_medicine.adapters.OrderMedicineRvAdapter
import com.example.onlineveterinarydoctor.presentation.nav_medicine.models.Medicines
import com.example.onlineveterinarydoctor.presentation.nav_medicine.models.Orders
import com.example.onlineveterinarydoctor.presentation.nav_patient.models.Patient
import com.example.onlineveterinarydoctor.utils.scope

class MedicineOrderScreen:Fragment(R.layout.screen_medicine_orderes) {

    private val binding by viewBinding(ScreenMedicineOrderesBinding::bind)
    private lateinit var orderMedicineRvAdapter : OrderMedicineRvAdapter
    private lateinit var ordersList : ArrayList<Orders>

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        loadData()
        orderMedicineRvAdapter =
            OrderMedicineRvAdapter(object:OrderMedicineRvAdapter.OnItemTouchClickListener {
                override fun onItem(orders : Orders) {

                }
            })
        orderMedicineRvAdapter.mySubmitList(ordersList)

        rvOrders.setHasFixedSize(true)
        rvOrders.adapter = orderMedicineRvAdapter

    }

    private fun loadData() {
        ordersList = ArrayList()

        for (i in 0 until 5) {
            ordersList.add(
                Orders(
                    id = i,
                    patient = Patient(
                        i ,
                        "Xakimova Feruza" ,
                        "feruza@mail.ru" ,
                        R.drawable.img ,
                        "999",
                        "Xonqa tum",
                        arrayListOf()
                    ),
                    Medicines(
                        i ,
                        "Otoclean Ear Cleaner" ,
                        "" ,
                        125800 ,
                        "Nematov A.B" ,
                        R.drawable.img_dogs_otoclean_pr
                    ),
                    3
                )
            )

        }
    }
}