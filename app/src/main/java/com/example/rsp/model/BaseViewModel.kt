package com.example.rsp.model

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.rsp.helper.NavigationListener

abstract class BaseViewModel(application: Application): ViewModel() {
    lateinit var navigationListener: NavigationListener
}