package com.cod3f1re.productositalika.ViewModel.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cod3f1re.productositalika.Model.Product
import com.cod3f1re.productositalika.R
import com.cod3f1re.productositalika.databinding.ItemProductBinding

class ProductAdapter(
    var productList: MutableList<Product>):
    ListAdapter<Product, ProductAdapter.ProductsViewHolder>(ProgramDiffUtilCallback) {

    var onItemClick: ((Product) -> Unit)? = null

    object ProgramDiffUtilCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemProductBinding.bind(itemView)

        init {
            binding.cardProduct.setOnClickListener {
                onItemClick?.invoke(productList[adapterPosition])
            }
        }

        fun bind(product: Product) {
            //Se establece una imagen desde internet
            Glide
                .with(binding.imageProduct.context)
                .load(product.image)
                .centerCrop()
                .override(200,200)
                .placeholder(R.drawable.load)
                .into(binding.imageProduct)

            // Se indica el nuevo texto
            binding.titleProduct.text = product.name

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product=getItem(position)
        holder.bind(product)
    }

    fun setFilteredList(listFiltered: ArrayList<Product>){
        productList = listFiltered
        notifyDataSetChanged()
    }

}


