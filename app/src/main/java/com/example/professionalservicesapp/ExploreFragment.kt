package com.example.professionalservicesapp

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import explore.R


class ExploreFragment : Fragment() {

    private val professionsList: MutableList<MutableList<String>> = mutableListOf()
    private val categoriesList: MutableList<String> = mutableListOf()
    private lateinit var categoryDisplayList: LinearLayout
    private lateinit var searchBar: EditText
    private lateinit var searchButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.explore_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryDisplayList = activity!!.findViewById(R.id.categories_list)
        searchBar = activity!!.findViewById(R.id.search_bar)
        searchButton = activity!!.findViewById(R.id.search_button)

        searchBar.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER)
        searchBar.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                event == null ||
                event.keyCode == KeyEvent.KEYCODE_ENTER) {
                sendSearchQuery()
                true
            }
            false
        }

        if(DataStore.professionDataReady.value==true) initLayout()
        DataStore.professionDataReady.observe(viewLifecycleOwner) {
            if (DataStore.professionDataReady.value == true) initLayout()
        }
    }

    private fun initLayout() {
        for(i in 0 until professionsList.size) {
            val category = professionsList[i]
            val categoryName = categoriesList[i]
            val categoryView: View = layoutInflater.inflate(R.layout.explore_category, null)
            categoryView.findViewById<TextView>(R.id.category_name).text = categoryName
            for(profession in category) {
                val professionView: View = layoutInflater.inflate(R.layout.explore_profession, null, true)
                professionView.findViewById<TextView>(R.id.profession_name).text = profession

                professionView.setOnClickListener {
                    sendSearchQuery()
                }

                categoryView.findViewById<LinearLayout>(R.id.profession_list).addView(professionView)
            }
            categoryDisplayList.addView(categoryView)
        }
    }

    private fun sendSearchQuery() {
        val text = searchBar.text.toString()
        val action = ExploreFragmentDirections.initiateSearch(searchQuery = text)
        findNavController().navigate(action)
    }

}