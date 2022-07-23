package com.example.professionalservicesapp

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object CurrentUser {
    lateinit var name: String
    lateinit var phoneNo: String
    val db = Firebase.firestore
    lateinit var userData: DocumentSnapshot

    fun loadUserData(user: FirebaseUser) {
        phoneNo = user.phoneNumber.toString()
        db.collection("Users").document(phoneNo).get().addOnSuccessListener { document ->
            userData = document
        }
    }

}