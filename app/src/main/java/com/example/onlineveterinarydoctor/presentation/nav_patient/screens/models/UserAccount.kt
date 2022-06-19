package com.example.onlineveterinarydoctor.presentation.nav_patient.screens.models

import java.io.Serializable

class UserAccount:Serializable {
    var uid : String? = null
    var displayName : String? = null
    var phoneNumber : String? = null
    var email : String? = null
    var photoUrl : String? = null
    var address : String? = null
    var myAnimals : HashMap<String , MyAnimal>? = null

    constructor()

    constructor(
        uid : String? ,
        displayName : String? ,
        email : String? ,
        photoUrl : String? ,
        phoneNumber : String? ,
        address : String? ,
        myAnimals : HashMap<String , MyAnimal>?
    ) {
        this.uid = uid
        this.displayName = displayName
        this.email = email
        this.photoUrl = photoUrl
        this.phoneNumber = phoneNumber
        this.address = address
        this.myAnimals = myAnimals
    }
}