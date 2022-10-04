package com.cod3f1re.productositalika.Utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.cod3f1re.productositalika.Model.ProductsSelected
import com.cod3f1re.productositalika.View.BuyProductsActivity


abstract class BaseActivity(): AppCompatActivity() {

    fun getProducts(): Int{
        ModelPreferencesManager.with(this)
        var products=0
        val productsSaved: ProductsSelected? =
            ModelPreferencesManager.get<ProductsSelected>("ProductsSelected")
        //Se verifica que no sea nulo, y que tenga de un elemento en adelante
        if(productsSaved!=null){
            if(productsSaved.products.isNotEmpty()){
                for(product in productsSaved.products){
                    products++
                }
            }
        }
        return products
    }
    fun openCart(){
        val intent = Intent(this, BuyProductsActivity::class.java)
        startActivity(intent)
    }
}