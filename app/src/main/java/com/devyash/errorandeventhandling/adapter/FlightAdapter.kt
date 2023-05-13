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

class FlightAdapter():PagingDataAdapter<Data,FlightAdapter.FlightViewHolder>(COMPARATOR) {

    inner class FlightViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var binding = FlightItemLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val viewHolder = FlightViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.flight_item_layout,parent,false))

        return viewHolder
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val data = getItem(position)

        holder.binding.personId.text = data?._id.toString()
        holder.binding.personName.text = data?.name.toString()
        holder.binding.personTrips.text = data?.trips.toString()

        holder.binding.flightName.text = data?.airline?.get(0)?.name.toString()
        holder.binding.flightCountry.text = data?.airline?.get(0)?.country.toString()
        holder.binding.flightSlogan.text = data?.airline?.get(0)?.slogan.toString()

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