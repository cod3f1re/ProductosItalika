package com.cod3f1re.productositalika.ViewModel.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cod3f1re.productositalika.Model.Product
import com.cod3f1re.productositalika.Model.ProductBuy
import com.cod3f1re.productositalika.R
import com.cod3f1re.productositalika.databinding.ItemProductBuyBinding

class ProductBuyAdapter(
    var productList:MutableList<ProductBuy>):
    ListAdapter<ProductBuy, ProductBuyAdapter.BuyProductsViewHolder>(ProgramDiffUtilCallback) {

    var onItemClick: ((ProductBuy) -> Unit)? = null

    object ProgramDiffUtilCallback : DiffUtil.ItemCallback<ProductBuy>() {
        override fun areItemsTheSame(
            oldItem: ProductBuy,
            newItem: ProductBuy
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductBuy,
            newItem: ProductBuy
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class BuyProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemProductBuyBinding.bind(itemView)

        init {
            binding.cardProduct.setOnClickListener {
                onItemClick?.invoke(productList[adapterPosition])
            }
        }

        fun bind(product: ProductBuy) {
            //Se establece una imagen desde internet
            Glide
                .with(binding.imageProduct.context)
                .load(product.image)
                .centerCrop()
                .override(240,200)
                .placeholder(R.drawable.load)
                .into(binding.imageProduct)

            // Se indica el nuevo texto
            binding.titleProduct.text = product.name

            binding.tvQuantity.text = "${product.count}"

            binding.tvPrice.text = "${product.price}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyProductsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_buy, parent, false)
        return BuyProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuyProductsViewHolder, position: Int) {
        val product=getItem(position)
        holder.bind(product)
    }

}
