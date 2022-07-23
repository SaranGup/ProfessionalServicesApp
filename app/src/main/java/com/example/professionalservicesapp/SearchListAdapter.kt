package com.example.professionalservicesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import explore.R

class SearchListAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.seach_result_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val u = DataStore.userData[position]
        (holder as ViewHolder).bind(u)
    }

    override fun getItemCount(): Int {
        return DataStore.userData.size
    }

//    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
//        super.onViewAttachedToWindow(holder)
//
//    }

    fun loadProfessionalData(id: String) {

    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        lateinit var user: SearchEntryModel
        private val profile: ImageView = ItemView.findViewById(R.id.profile_pic)
        private val name: TextView = ItemView.findViewById(R.id.name)
        private val profession: TextView = ItemView.findViewById(R.id.profession)
        private val ratings: RatingBar = ItemView.findViewById(R.id.rating)
        private val charge: TextView = ItemView.findViewById(R.id.charge)
        private val descriptionButton: LinearLayout = ItemView.findViewById(R.id.description_button)
        private val descriptionText: TextView = ItemView.findViewById(R.id.description_text)

        fun bind(userData: SearchEntryModel) {
            user = userData
            name.text = user.name
            profession.text = user.profession
            ratings.progress = (user.rating * 20).toInt()
            charge.text = user.rate.toString()
            descriptionText.text = user.description
            descriptionButton.setOnClickListener {
                if(descriptionText.isVisible) {
                    descriptionText.visibility = View.GONE
                }
                else {
                    descriptionText.visibility = View.VISIBLE
                }
            }

            Picasso.get().load(user.profileLink).into(profile)
        }
    }
}