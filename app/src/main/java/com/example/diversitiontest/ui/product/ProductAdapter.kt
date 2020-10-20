package com.example.diversitiontest.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diversitiontest.R
import com.example.diversitiontest.databinding.ViewProductBinding
import com.example.diversitiontest.model.Product

class ProductAdapter( val onClick: ((String) -> Unit)) : ListAdapter<Product, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Product>() {

   override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = DataBindingUtil.inflate<ViewProductBinding>(
            LayoutInflater.from(parent.context),
            R.layout.view_product,
            parent,
            false
        )

        return ProductViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductViewHolder).bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ViewProductBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                binding.product?.productId?.also {

                    onClick.invoke(it)

                }
            }
        }


        fun bind(product: Product) {
            binding.product = product
        }
    }

}