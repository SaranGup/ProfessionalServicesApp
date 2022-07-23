package com.example.professionalservicesapp

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import explore.R

class ExploreActivity : AppCompatActivity() {

//    private val categories: List<String> = listOf(
//        "Repairs and Renovations",
//        "Cosmetics, salon and spa",
//        "Household Chores and general help",
//        "Cleaning and Pest Control",
//        "Care"
//    )

    //    private val categoriesList: List<List<String>> = listOf(
//        listOf("Carpentering", "Plumbing", "Electrical Work", "Wall Painting", "Mechanic", "Masonry"),
//        listOf("Men's Hairdressing", "Women's Hairdressing", "Men's Massage", "Women's Massage"),
//        listOf("Cooking", "Maid/ Butler", "Driving", "Handyman", "Gardening"),
//        listOf("Pressure Washing", "Carpet cleaning", "Pest Control"),
//        listOf("Nurse", "Babysitter", "Tutor")
//    )

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.explore_layout)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.explore_fragment) as NavHostFragment
        navController = navHostFragment.navController

        DataStore.loadProfessionData()
        DataStore.reloadUserData()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }
}