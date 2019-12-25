package com.example.rsp.ui.main.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rsp.AppConstants
import com.example.rsp.R
import com.example.rsp.databinding.MainFragmentBinding
import com.example.rsp.helper.NavigationListener
import com.example.rsp.ui.main.screens.playground.PlaygroundFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), NavigationListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.navigationListener = this
    }

    override fun goNext(id: Int) {
        val bundle = Bundle()
        bundle.putInt(AppConstants.SELECTED_MODE, id)
        val fragment = PlaygroundFragment.newInstance()
        fragment.arguments = bundle
        fragmentManager?.beginTransaction()
            ?.replace(R.id.container, fragment)
            ?.addToBackStack(this.javaClass.name)
            ?.commit()
    }
}
