package com.example.jiangzizheng.waterloocarpool.frontend.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jiangzizheng.waterloocarpool.R
import com.example.jiangzizheng.waterloocarpool.backend.bean.Trip
import com.example.jiangzizheng.waterloocarpool.frontend.activities.TripDetailsActivity

class UserAdapter (
    private var trips: MutableList<Trip>
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    companion object {
        val sUserAdapter = UserAdapter(mutableListOf())
    }

    class ViewHolder(val line: LinearLayout) : RecyclerView.ViewHolder(line) {
        val firstName: TextView = line.findViewById(R.id.line_trip_firstName)
        val departTime: TextView = line.findViewById(R.id.line_trip_dTime)
    }

    fun add(trip: Trip) {
        trips.add(trip)
        notifyDataSetChanged()
    }

    override fun getItemCount() = trips.size

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
        val entry = trips[position]
        val context = holder.line.context
        val background = context.getDrawable(R.drawable.trip_line_background)

        holder.firstName.text = entry.driverName
        // holder.departTime.text = entry.dDate.toDate().time.toString()
        val dd = entry.dDate.toDate()
        val hours = (dd.hours).toString()
        val minutes = dd.minutes.toString()
        holder.departTime.text = hours + ":" + minutes
        holder.firstName.background = background
        holder.departTime.background = background
    }
}