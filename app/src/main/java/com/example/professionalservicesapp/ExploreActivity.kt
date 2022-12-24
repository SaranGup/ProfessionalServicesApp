package com.example.professionalservicesapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import explore.R

class ExploreActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.explore_layout)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { permitted->
            if(!permitted) {
                locationAlert()
            }
        }

        locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationAlert()
        }
        else {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location?->
                location?.let { LocationStore.loadLocation(it, this) }
            }
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.explore_fragment) as NavHostFragment
        navController = navHostFragment.navController

        DataStore.loadProfessionData()
        DataStore.reloadUserData()
    }

    fun locationAlert() {

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }
}