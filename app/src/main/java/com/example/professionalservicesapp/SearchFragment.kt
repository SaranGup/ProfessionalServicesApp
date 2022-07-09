package com.example.professionalservicesapp

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import explore.R

class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var searchQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            searchQuery = it.getString(searchQuery)
        }
    }

    private val db = Firebase.firestore
    private lateinit var searchList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.explore_search, container, false)
        activity!!.findViewById<EditText>(R.id.search_bar).text = Editable.Factory.getInstance().newEditable(searchQuery)
        searchList = activity!!.findViewById(R.id.search_list)

        if(DataStore.userDataReady.value==true) afterDataReady()
        else DataStore.userDataReady.observe(viewLifecycleOwner) {afterDataReady()}

        return view
    }

    private fun afterDataReady() {
        DataStore.setUnready()

    }
}