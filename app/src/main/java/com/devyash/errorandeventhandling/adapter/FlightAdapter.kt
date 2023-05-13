package com.devyash.errorandeventhandling.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.devyash.errorandeventhandling.R
import com.devyash.errorandeventhandling.databinding.FlightItemLayoutBinding
import com.devyash.errorandeventhandling.models.passenger.Data
import com.devyash.errorandeventhandling.paging.FlightPagingSource

class FlightAdapter(val flightData:List<Data>):PagingDataAdapter<Data,FlightAdapter.FlightViewHolder>(COMPARATOR) {

    inner class FlightViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var binding = FlightItemLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val viewHolder = FlightViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.flight_item_layout,parent,false))

        return viewHolder
    }

    override fun getItemCount(): Int {
        return flightData.size
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val data = flightData[position]

        holder.binding.personId.text = data._id
        holder.binding.personName.text = data.name
        holder.binding.personTrips.text = data.trips.toString()

        holder.binding.flightName.text = data.airline.get(0).name
        holder.binding.flightCountry.text = data.airline.get(0).country
        holder.binding.flightSlogan.text = data.airline.get(0).slogan

    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<Data>(){
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }

        }
    }
}