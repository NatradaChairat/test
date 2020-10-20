package com.example.diversitiontest.ui.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.diversitiontest.databinding.FragmentProductConfirmationBinding
import com.example.diversitiontest.model.common.Status
import com.example.diversitiontest.ui.product.ProductFragmentArgs
import kotlinx.android.synthetic.main.fragment_product.*
import org.koin.android.viewmodel.ext.android.viewModel


class ProductConfirmationFragment : Fragment() {

    private lateinit var binding: FragmentProductConfirmationBinding
    private val viewModel: ProductConfirmationViewModel by viewModel()

    private val args: ProductConfirmationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductConfirmationBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.productId.value = args.productId
        viewModel.quantity.value = args.quantity
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.btnPurchase.setOnClickListener {
            context?.let {
                AlertDialog.Builder(it)
                    .setTitle("Purchase Confirmation")
                    .setMessage("Press ok to confirm purchase")
                    .setNegativeButton("cancel") { _, _ -> }
                    .setPositiveButton("ok") { _, _ ->
                        viewModel.purchase().observe(viewLifecycleOwner, Observer { response ->
                            when(response.status){
                                Status.SUCCESS ->{
                                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()

                                }
                                Status.ERROR ->{
                                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                                }
                                Status.LOADING ->{

                                }
                            }
                        })
                    }
                    .show()
            }
        }
    }

    private fun initObservers() {

    }

}