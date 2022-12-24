package com.example.professionalservicesapp

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import explore.R

class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val searchQuery: SearchFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var searchList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.explore_search, container, false)
        requireActivity().findViewById<EditText>(R.id.search_bar).text = Editable.Factory.getInstance().newEditable(searchQuery.searchQuery)
        searchList = view.findViewById(R.id.search_list)
        searchList.layoutManager = LinearLayoutManager(this.context)
        val adapter = SearchListAdapter()
        searchList.adapter = adapter

        if(DataStore.userDataReady.value==true) afterDataReady()
        else DataStore.userDataReady.observe(viewLifecycleOwner) {afterDataReady()}

        return view
    }

    private fun afterDataReady() {
        searchQuery.searchQuery?.let {
            DataStore.setOrderByQuery(it)
            DataStore.setUnready()
        }

        fun setRecyclerView() {
            searchList.adapter?.notifyDataSetChanged()
        }

        if(DataStore.userDataReady.value==true) setRecyclerView()
        else DataStore.userDataReady.observe(viewLifecycleOwner) {setRecyclerView()}
    }
}