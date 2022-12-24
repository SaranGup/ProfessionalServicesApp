package com.example.professionalservicesapp

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.MutableLiveData
import java.util.*

object LocationStore {
    var location: Location? = null
    lateinit var address: MutableLiveData<Address?>

    fun loadLocation(updatedLoc: Location, context: Context) {
        location = updatedLoc
        address.value = Geocoder(context, Locale.getDefault()).getFromLocation(location!!.latitude, location!!.longitude, 1)
            ?.get(0)
    }
}