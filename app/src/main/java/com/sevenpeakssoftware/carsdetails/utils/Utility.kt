package com.sevenpeakssoftware.carsdetails.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
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

fun showToastMessage(context: Context, message: String?) {
    message?.let {
        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
    }
}

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }
