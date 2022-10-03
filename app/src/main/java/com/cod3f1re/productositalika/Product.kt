package com.cod3f1re.productositalika

import java.io.Serializable

data class Product(
    val id:Int=-1,
    val name:String="",
    val price: Int=-1,
    val description:String="",
    val image: String=""
): Serializable