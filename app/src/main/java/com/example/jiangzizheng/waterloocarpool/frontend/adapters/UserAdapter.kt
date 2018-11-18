package com.example.jiangzizheng.waterloocarpool.frontend.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jiangzizheng.waterloocarpool.R
import com.example.jiangzizheng.waterloocarpool.frontend.activities.TripDetailsActivity

class UserAdapter (
    private var trips: LinkedHashMap<String, String>
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(val line: LinearLayout) : RecyclerView.ViewHolder(line) {
        val firstName: TextView = line.findViewById(R.id.line_trip_fristName)
        val departTime: TextView = line.findViewById(R.id.line_trip_dTime)
    }

    fun add(firstName: String, departTime: String) {
        trips[firstName] = departTime
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return when {
            trips.isEmpty() -> 1
            else -> trips.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).run {
            inflate(R.layout.line_trip, parent, false) as LinearLayout
        }).apply {
            line.setOnClickListener {
                val context = parent.context
                context.startActivity(Intent(context, TripDetailsActivity::class.java))
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = ArrayList(trips.entries)[position]
        val context = holder.line.context
        val background = context.getDrawable(R.drawable.trip_line_background)

        holder.firstName.text = entry.key
        holder.departTime.text = entry.value
        holder.firstName.background = background
        holder.departTime.background = background
    }
}