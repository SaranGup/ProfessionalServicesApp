package com.example.professionalservicesapp

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object DataStore {

    val professionsList: MutableList<MutableList<String>> = mutableListOf()
    val professionsCategoryList: MutableList<String> = mutableListOf()
    val userData: MutableList<SearchEntryModel> = mutableListOf()

    private val db = Firebase.firestore

    val userDataReady: MutableLiveData<Boolean> = MutableLiveData(false)
    val professionDataReady: MutableLiveData<Boolean> = MutableLiveData(false)

    fun setUnready() {userDataReady.value = false}

    fun stringScore(str: String, comp: String) {

    }

    fun setOrderByQuery(str: String) {

    }

    fun loadProfessionData() {
        db.collection("Profession Categories").orderBy("index").get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d(ContentValues.TAG, "DocumentSnapshot data: $document")

                val categories = document

                fun loadProfessions(index: Int) {
                    val category = categories.elementAt(index)
                    val name = category.id
                    professionsCategoryList.add(name)
                    val pList = mutableListOf<String>()

                    db.collection("Professions").whereEqualTo("type", name).orderBy("index").get().addOnSuccessListener { document ->

                        for(profession in document) {
                            pList.add(profession.id)
                        }

                        professionsList+=pList
                        if(index<categories.size()-1) loadProfessions(index+1)

                    }.addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "get failed with ", exception)
                        loadProfessions(index)
                    }
                }
                loadProfessions(0)

            } else {
                Log.d(ContentValues.TAG, "No such document")
            }
            professionDataReady.value = true
        }.addOnFailureListener {
            loadProfessionData()
        }
    }

    fun reloadUserData() {

        db.collection("Users").whereEqualTo("Professional", true).
            get().addOnSuccessListener { documents ->

            for(user in documents) {
                val email = user.id
                val name = user.get("Name").toString()
                val profession = user.get("Profession").toString()
                val ratings = user.get("Ratings").toString().toDouble()
                val chargeType = user.get("Rate Type").toString()
                val rate = user.get("Rate").toString().toDouble()
                val location = user.get("Location").toString()

                userData.add(SearchEntryModel(email, name, profession, ratings, chargeType, rate, location))
            }
            userDataReady.value = true
        }.addOnFailureListener {
            reloadUserData()
        }
    }
}