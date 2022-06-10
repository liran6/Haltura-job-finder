package com.example.haltura.Utils

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyAllObservers() {
    this.value = this.value
}