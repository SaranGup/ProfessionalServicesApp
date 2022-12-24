package com.example.professionalservicesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.hbb20.CountryCodePicker
import explore.R


class EnterPhoneFragment : Fragment() {

    private lateinit var ccp: CountryCodePicker
    private lateinit var phoneEnter: EditText
    private lateinit var button: Button

    interface OnEnterPhone {
        fun onEnterPhone(phoneNo: String)
    }

    private lateinit var pListener: OnEnterPhone

    fun setListenerPhone(listener: OnEnterPhone) {
        pListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_enter_phone, container, false)

        ccp = view.findViewById(R.id.ccp)
        phoneEnter = view.findViewById(R.id.phone_box)
        button = view.findViewById(R.id.enter_phone)

        ccp.registerCarrierNumberEditText(phoneEnter)

        button.setOnClickListener {
            pListener.onEnterPhone(ccp.fullNumberWithPlus.replace(" ",""))
        }

        return view
    }

}