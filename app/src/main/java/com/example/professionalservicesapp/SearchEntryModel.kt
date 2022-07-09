package com.example.professionalservicesapp

import android.location.Location

data class SearchEntryModel(
    val email: String,
    val name: String,
    val profession: String,
    val rating: Double,
    val chargeType: String,
    val rate: Double,
    val location: String
)