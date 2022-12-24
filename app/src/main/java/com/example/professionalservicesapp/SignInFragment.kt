package com.example.professionalservicesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import explore.R


class SignInFragment : Fragment() {

    interface OnSignIn{
        fun onClickSignIn()
    }

    lateinit var sListener: OnSignIn

    fun setOnSignIn(listener: OnSignIn) {
        sListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        view.findViewById<Button>(R.id.signInButton)?.setOnClickListener {
            sListener.onClickSignIn()
        }
        return view
    }
}