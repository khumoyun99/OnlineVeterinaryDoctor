package com.example.onlineveterinarydoctor.presentation.nav_login.models

import com.example.onlineveterinarydoctor.presentation.nav_medicine.models.Medicines
import java.io.Serializable

class Account:Serializable {
    var uid : String? = null
    var displayName : String? = null
    var phoneNumber : String? = null
    var email : String? = null
    var photoUrl : String? = null
    var address : String? = null
    var myMedicine : HashMap<String , Medicines?>? = null

    constructor(
        uid : String ,
        displayName : String ,
        email : String ,
        photoUrl : String ,
        phoneNumber : String ,
        address : String ,
        myMedicine : HashMap<String , Medicines?>?

    ) {
        this.uid = uid
        this.displayName = displayName
        this.email = email
        this.photoUrl = photoUrl
        this.phoneNumber = phoneNumber
        this.address = address
        this.myMedicine = myMedicine
    }



    constructor()

    constructor(
        uid : String? ,
        displayName : String? ,
        phoneNumber : String? ,
        email : String? ,
        photoUrl : String?
    ) {
        this.uid = uid
        this.displayName = displayName
        this.phoneNumber = phoneNumber
        this.email = email
        this.photoUrl = photoUrl
    }

}