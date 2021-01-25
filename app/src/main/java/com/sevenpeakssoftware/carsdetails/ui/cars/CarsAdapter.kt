package com.sevenpeakssoftware.carsdetails.ui.cars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sevenpeakssoftware.carsdetails.databinding.ItemCarsBinding
import com.sevenpeakssoftware.carsdetails.utils.clearLoading
import com.sevenpeakssoftware.carsdetails.utils.load

class CarsAdapter(): ListAdapter<CarsModel, CarsAdapter.CarsViewHolder>(CarsItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        return CarsViewHolder(ItemCarsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarsViewHolder(private val binding: ItemCarsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CarsModel) {
            binding.imgCars.clearLoading()
            binding.imgCars.load(item.image)
            binding.tvTitle.text = item.title
            binding.tvDate.text = item.date
            binding.tvIngress.text = item.ingress
        }
    }
}

private class CarsItemCallback : DiffUtil.ItemCallback<CarsModel>() {

    override fun areItemsTheSame(oldItem: CarsModel, newItem: CarsModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CarsModel, newItem: CarsModel): Boolean {
        return oldItem == newItem
    }
}



