package com.example.diversitiontest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.diversitiontest.binding.BindingAdapters
import com.example.diversitiontest.databinding.FragmentHomeBinding
import com.example.diversitiontest.model.common.Status
import com.example.diversitiontest.ui.product.ProductAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()

    private val mAdapter = ProductAdapter(){
        HomeFragmentDirections.actionNavigationHomeToNavigationProduct(it).apply {
            findNavController().navigate(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        homeBinding.lifecycleOwner = viewLifecycleOwner
        homeBinding.viewModel = viewModel

        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewModel.products.observe(viewLifecycleOwner, Observer { response ->
            when (response.status) {
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE

                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE

                    mAdapter.submitList(response.data)
                }
            }

        })
    }

    private fun initViews() {
        rvProducts.adapter = mAdapter
    }
}