package com.example.diversitiontest.ui.product

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.diversitiontest.databinding.FragmentProductBinding
import kotlinx.android.synthetic.main.fragment_product.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProductFragment : Fragment() {

    private lateinit var productBinding: FragmentProductBinding
    private val viewModel: ProductViewModel by viewModel()
    private val args: ProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productBinding = FragmentProductBinding.inflate(inflater, container, false)
        productBinding.lifecycleOwner = viewLifecycleOwner
        productBinding.viewModel = viewModel

        return productBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.productId.value = args.productId

        initViews()
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        productBinding.btnConfirm.setOnClickListener {
            ProductFragmentDirections.actionNavigationProductToNavigationProductConfirmation(
                args.productId,
                viewModel.quantity.value ?: 0
            ).also {
                findNavController().navigate(it)
            }
        }
    }

    private fun initObservers() {
        viewModel.product.observe(viewLifecycleOwner, Observer { response ->
            response?.stock?.also { stock ->
                val stockList = (1..stock).toList()
                context?.let {
                    ArrayAdapter(it, R.layout.simple_spinner_item, stockList).also { arrayAdapter ->
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner.adapter = arrayAdapter

                        spinner.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onNothingSelected(parent: AdapterView<*>?) {}

                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    viewModel.quantity.value = stockList[position]
                                }
                            }
                    }
                }
            }

        })
    }

    private fun initViews() {
    }
}