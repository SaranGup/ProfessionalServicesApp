package com.example.professionalservicesapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import explore.R


class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_layout)
        findViewById<Button>(R.id.register_button).setOnClickListener { createSignInIntent() }
    }

    fun createSignInIntent() {
        val authenticationMethods = arrayListOf(
            AuthUI.IdpConfig.PhoneBuilder().build()
        )

        val signInIntent  = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(authenticationMethods)
            .build()



        signInLauncher.launch(signInIntent)
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if(result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                CurrentUser.loadUserData(user)
            }
        }
    }

}