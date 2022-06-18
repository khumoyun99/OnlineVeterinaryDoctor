package com.example.onlineveterinarydoctor.presentation.nav_login.models

import java.io.Serializable

class Account:Serializable {
    var uid : String? = null
    var displayName : String? = null
    var email : String? = null
    var photoUrl : String? = null
    var phoneNumber : String? = null

    constructor(
        uid : String ,
        displayName : String ,
        phoneNumber : String ,
        email : String ,
        photoUrl : String ,
    ) {
        this.uid = uid
        this.displayName = displayName
        this.phoneNumber = phoneNumber
        this.email = email
        this.photoUrl = photoUrl
    }

    constructor()

    constructor(
        displayName : String? ,
        email : String? ,
        photoUrl : String? ,
        phoneNumber : String?
    ) {
        this.displayName = displayName
        this.email = email
        this.photoUrl = photoUrl
        this.phoneNumber = phoneNumber
    }


}