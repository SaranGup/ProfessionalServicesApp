package com.example.professionalservicesapp

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlin.math.min

object DataStore {

    val professionsList: MutableMap<String, MutableList<Profession>> = mutableMapOf()
    val userData: MutableList<SearchEntryModel> = mutableListOf()

    val scope: CoroutineScope = MainScope()

    private val db = Firebase.firestore

    val userDataReady: MutableLiveData<Boolean> = MutableLiveData(false)
    val professionDataReady: MutableLiveData<Boolean> = MutableLiveData(false)

    fun setUnready() {userDataReady.value = false}

    fun stringScore(str: String, comp: String): Int {
        val distanceTable: MutableList<MutableList<Int>> = mutableListOf()

        for(i in 0 until str.length) {
            distanceTable.add(mutableListOf())
            for(j in 0 until comp.length) {
                distanceTable[i].add(0)
            }
        }

        if(str[0] != comp[0]) distanceTable[0][0] = 1

        val maxSize: Int = maxOf(str.length, comp.length)

        for(s in 1 until (maxSize*2-1)) {
            for(i in 0..s) {
                val j = s-i

                if(i>=str.length || j>=comp.length) continue


                var distA: Int = Int.MAX_VALUE
                var distB: Int = Int.MAX_VALUE
                var distC: Int = Int.MAX_VALUE

                if(j>0) distA = distanceTable[i][j-1] + 1
                if(i>0) distB = distanceTable[i-1][j] + 1
                if(j>0&&i>0) {
                    if(str[i] == comp[j]) distC = distanceTable[i-1][j-1]
                    else distC = distanceTable[i-1][j-1] + 1
                }

                distanceTable[i][j] = min(distA, min(distB, distC))
            }
        }

        return distanceTable.last().last()
    }

    fun setOrderByQuery(str: String) {

        scope.cancel()
        scope.launch {
            for(user in userData) {
                user.sortScore = stringScore(user.name, str) + stringScore(user.profession, str)
            }
            userData.sortBy { it.sortScore }
            userDataReady.value = true
        }
    }

    fun loadProfessionData() {
        db.collection("Profession Categories").orderBy("index").get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d(ContentValues.TAG, "DocumentSnapshot data: $document")

                val categories = document
                var index = 0

                fun getCategory(name: String, index: Int) {
                    db.collection("Professions").whereEqualTo("type", name).orderBy("index").get().addOnSuccessListener { document ->
                        if(document==null||document.size()==0) {
                            getCategory(name, index)
                        }
                        else {
                            for(profession in document) {
                                professionsList[profession.get("type")]?.add(Profession(profession.id, profession.get("type").toString(), profession.get("Picture Link").toString()))
                            }
                            val pl = professionsList
                            val size = categories.size()
                            if(name == categories.last().id) professionDataReady.value = true
                        }
                    }.addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "get failed with ", exception)
                        getCategory(name, index)
                    }
                }
                repeat(categories.size()) {
                    val category = categories.elementAt(index)
                    val name = category.id
                    professionsList[name] = mutableListOf()

                    getCategory(name, index)

                    index++
                }

//                fun loadProfessions(index: Int) {
//                    val category = categories.elementAt(index)
//                    val name = category.id
//                    professionsCategoryList.add(name)
//                    val pList = mutableListOf<String>()
//
//                    db.collection("Professions").whereEqualTo("type", name).orderBy("index").get().addOnSuccessListener { document ->
//
//                        for(profession in document) {
//                            pList.add(profession.id)
//                        }
//
//                        professionsList+=pList
//                        if(index<categories.size()-1) loadProfessions(index+1)
//
//                    }.addOnFailureListener { exception ->
//                        Log.d(ContentValues.TAG, "get failed with ", exception)
//                        loadProfessions(index)
//                    }
//                }
//                loadProfessions(0)

            } else {
                Log.d(ContentValues.TAG, "No such document")
            }
        }.addOnFailureListener {
            loadProfessionData()
        }
    }

    fun reloadUserData() {

        db.collection("Users").whereEqualTo("Professional", true).
            get().addOnSuccessListener { documents ->

            for (user in documents) {
                val email = user.id
                val name = user.get("Name").toString()
                val profession = user.get("Profession").toString()
                val ratings = user.get("Ratings").toString().toDouble()
                val chargeType = user.get("Rate Type").toString()
                val rate = user.get("Rate").toString().toDouble()
                val description = user.get("Summary").toString()
                val profileLink = user.get("Profile Link").toString()
                val location = user.get("Location").toString()

                userData.add(
                    SearchEntryModel(
                        email,
                        name,
                        profession,
                        ratings,
                        chargeType,
                        rate,
                        location,
                        description,
                        profileLink,
                        0
                    )
                )
            }
            userDataReady.value = true
        }
//        }.addOnFailureListener {
//            reloadUserData()
//        }
    }
}