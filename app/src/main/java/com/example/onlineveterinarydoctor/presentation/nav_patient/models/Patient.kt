package com.example.onlineveterinarydoctor.presentation.nav_patient.models

import java.io.Serializable

data class Patient(
    val id : Int ,
    val name : String ,
    val email : String ,
    val img : Int ,
    val phoneNumber : String ,
    val address : String ,
    val animalsList : ArrayList<Animal>
):Serializable

data class Animal(
    val id : Int ,
    val type : String ,
    val name : String ,
    val age : String ,
    val color : String ,
    val animalsType : String ,
    val gender : String ,
    val weight : String ,
    val widthHeight : String ,
    val image : Int ,
    val additionalInfo : String
):Serializable