package com.example.professionalservicesapp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import explore.R
import java.util.concurrent.TimeUnit


class SignInActivity : AppCompatActivity() {

    private lateinit var phone: String
    private lateinit var auth: FirebaseAuth
    lateinit var verificationID: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var credential: PhoneAuthCredential
    lateinit var phoneFragment: EnterPhoneFragment
    lateinit var otpFragment: EnterOTPFragment
    var prevFragment: Fragment? = null

    private val callbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            credential = p0
            signInWithPhoneAuthCredential()
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            TODO("Not yet implemented")
        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            verificationID = p0
            resendToken = p1
            displayFragment(otpFragment)
        }
    }

    fun sendOTP() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential() {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    userSignedIn()
                    finish()
                } else {

                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.sign_in_layout)
        val signIn =  SignInFragment()
        phoneFragment = EnterPhoneFragment()
        otpFragment = EnterOTPFragment()

        val user = Firebase.auth.currentUser
        if(user != null) {
            userSignedIn()
            return
        }

        signIn.setOnSignIn(object: SignInFragment.OnSignIn {
            override fun onClickSignIn() {
                displayFragment(phoneFragment)
            }
        })

        phoneFragment.setListenerPhone(object: EnterPhoneFragment.OnEnterPhone {
            override fun onEnterPhone(phoneNo: String) {
                phone = phoneNo
                sendOTP()
                displayFragment(otpFragment)
            }
        })

        otpFragment.setListenerOtp(object: EnterOTPFragment.OnEnterOtp {
            override fun onEnterOtp(code: String) {
                credential = PhoneAuthProvider.getCredential(verificationID, code)
                signInWithPhoneAuthCredential()
            }
        })

/*        val user = firebase.auth().currentUser

        if(user!=null) {
            userSignedIn()
        }*/

        displayFragment(signIn)
    }

    fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.sign_in_fragments, fragment).commit()
    }

    private fun userSignedIn() {
        val intent = Intent(this@SignInActivity, ExploreActivity::class.java)
        FirebaseAuth.getInstance().currentUser?.let { CurrentUser.loadUserData(it) }
        startActivity(intent)
    }

    private fun getUserData() {

    }

/*    fun createSignInIntent() {
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
    }*/

}