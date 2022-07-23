package com.example.professionalservicesapp

import android.location.Location

data class SearchEntryModel(
    var email: String,
    var name: String,
    var profession: String,
    var rating: Double,
    var chargeType: String,
    var rate: Double,
    var location: String,
    var description: String,
    val profileLink: String,
    var sortScore: Int = 0
)