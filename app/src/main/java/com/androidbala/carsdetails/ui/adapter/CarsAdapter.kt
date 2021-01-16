package com.androidbala.carsdetails.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidbala.carsdetails.R
import com.androidbala.carsdetails.ui.model.CarsModel
import com.androidbala.carsdetails.utils.clearLoading
import com.androidbala.carsdetails.utils.load
import kotlinx.android.synthetic.main.item_cars.view.*

class CarsAdapter: ListAdapter<CarsModel, CarsAdapter.CarsViewHolder>(ArticleItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        return CarsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_cars, parent, false)) {

        fun bind(item: CarsModel) {
            itemView.imgCarPhoto.clearLoading()
            itemView.imgCarPhoto.load(item.image)
            itemView.tvTitle.text   = item.title
            itemView.tvDate.text    = item.date
            itemView.tvDetails.text = item.ingress
        }

    }
}

private class ArticleItemCallback : DiffUtil.ItemCallback<CarsModel>() {

    override fun areItemsTheSame(oldItem: CarsModel, newItem: CarsModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CarsModel, newItem: CarsModel): Boolean {
        return oldItem == newItem
    }
}



