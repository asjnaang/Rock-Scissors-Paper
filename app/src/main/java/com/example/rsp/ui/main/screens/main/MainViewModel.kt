package com.example.rsp.ui.main.screens.main

import androidx.lifecycle.ViewModel
import com.example.rsp.helper.NavigationListener

class MainViewModel : ViewModel() {
    lateinit var navigationListener: NavigationListener
    fun onChoosingPlayMode(id: Int) {
        navigationListener?.goNext(id)
    }
}
