package com.sevenpeakssoftware.carsdetails.utils

open class ErrorHandler<out T>(private val _data: T) {

    var consumed = false
        private set

    val data: T?
        @Synchronized get() {
            return if (consumed) {
                null
            } else {
                consumed = true
                _data
            }
        }

    fun peek(): T = _data
}