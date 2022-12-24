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

    interface RegisterUser {
        fun getUserData()
    }

    lateinit var rListener: RegisterUser

    fun seRtListener(listener: RegisterUser) {
        rListener = listener
    }

    fun loadUserData(user: FirebaseUser) {
        db.collection("Users").document(phoneNo).get().addOnSuccessListener { document ->
            if(document!=null) userData = document
            else {
                rListener.getUserData()

            }
        }
    }

}