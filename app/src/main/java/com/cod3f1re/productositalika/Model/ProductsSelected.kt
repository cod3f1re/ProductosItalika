package com.cod3f1re.productositalika.Model

import com.cod3f1re.productositalika.Model.Product
import java.io.Serializable

data class ProductsSelected(
    var products: ArrayList<ProductBuy> = ArrayList()
): Serializable
