package com.example.pricify.model
import java.io.Serializable

class User:Serializable {
    var fullName:String=""
    var email:String=""
    constructor(){

    }
    constructor(fullName: String, email: String) {
        this.fullName = fullName
        this.email = email
    }
}