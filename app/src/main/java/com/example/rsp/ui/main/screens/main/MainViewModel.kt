package com.example.rsp.ui.main.screens.main

import android.app.Application
import com.example.rsp.model.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {
    fun onChoosingPlayMode(id: Int) {
        navigationListener?.goNext(id)
    }
}
