package com.example.rsp

import com.example.rsp.ui.main.screens.main.MainViewModel
import com.example.rsp.ui.main.screens.playground.PlaygroundViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel { MainViewModel() }
    viewModel { PlaygroundViewModel() }
}