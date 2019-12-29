package com.example.rsp.helper

import androidx.databinding.Observable
import androidx.databinding.ObservableInt

fun <T : Observable> T.addOnPropertyChanged(callback: (T) -> Unit) =
    object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable?, i: Int) =
            callback(observable as T)
    }.also { addOnPropertyChangedCallback(it) }


fun Int?.filterNull(defaultValue: Int = 0): Int {
    return this ?: defaultValue
}

fun ObservableInt.plusOne() {
    this.set(this.get() + 1)
}