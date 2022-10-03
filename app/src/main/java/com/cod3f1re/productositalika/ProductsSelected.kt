package com.cod3f1re.productositalika

import java.io.Serializable

data class ProductsSelected(
    var products: ArrayList<Product> = ArrayList()
): Serializable
