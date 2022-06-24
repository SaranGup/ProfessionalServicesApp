package explore

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CategoryListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val categoryList: List<String> = listOf(
        "Home Repairs/Renovations",
        "Household Assistance",
        "Beauty and Cosmetics",
        "Miscellaneous"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}