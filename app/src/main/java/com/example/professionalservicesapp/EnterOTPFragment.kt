package com.example.professionalservicesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import explore.R

class EnterOTPFragment() : Fragment() {

    interface OnEnterOtp {
        fun onEnterOtp(code: String)
    }

    private lateinit var oListener: OnEnterOtp

    fun setListenerOtp(listener: OnEnterOtp) {
        oListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_enter_o_t_p, container, false)

        val button: Button = view.findViewById(R.id.check_otp)
        val otpBox: EditText = view.findViewById(R.id.otp_box)

        button.setOnClickListener {
            oListener.onEnterOtp(otpBox.text.toString())
        }
        
        return view
    }
}