package com.androidbala.carsdetails.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(path: String) {
    Glide.with(this)
        .load(path)
        .into(this)
}

fun ImageView.clearLoading() {
    Glide.with(this)
        .clear(this)
}