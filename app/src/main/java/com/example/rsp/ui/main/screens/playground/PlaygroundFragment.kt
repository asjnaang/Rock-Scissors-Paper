package com.example.rsp.ui.main.screens.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rsp.databinding.PlaygroundFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaygroundFragment : Fragment() {
    companion object {
        fun newInstance() = PlaygroundFragment()
    }

    private val viewModel: PlaygroundViewModel by viewModel()
    private lateinit var binding: PlaygroundFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PlaygroundFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

}
