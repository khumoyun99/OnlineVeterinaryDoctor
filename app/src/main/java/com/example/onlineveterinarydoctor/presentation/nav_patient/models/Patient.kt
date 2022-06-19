package com.example.onlineveterinarydoctor.presentation.nav_patient.models

import java.io.Serializable

data class Patient(
    val id : String ,
    val name : String ,
    val email : String ,
    val img : String ,
    val phoneNumber : String ,
    val address : String ,
    val animalsList : ArrayList<Animal>? = null
):Serializable

data class Animal(
    val id : String ,
    val type : String ,
    val name : String ,
    val age : String ,
    val color : String ,
    val animalsType : String ,
    val gender : String ,
    val weight : String ,
    val widthHeight : String ,
    val image : String ,
    val additionalInfo : String
):Serializable