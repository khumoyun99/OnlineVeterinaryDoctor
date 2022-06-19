package com.example.onlineveterinarydoctor.presentation.nav_medicine.models

import com.example.onlineveterinarydoctor.presentation.nav_patient.models.Patient

data class Orders(
    val id : Int ,
    val patient : Patient ,
//    val medicines : Medicines ,
    val quantity : Int
)