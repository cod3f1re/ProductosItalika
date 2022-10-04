package com.cod3f1re.productositalika.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cod3f1re.productositalika.Model.Product
import com.cod3f1re.productositalika.Model.ProductBuy
import com.cod3f1re.productositalika.Model.ProductsSelected
import com.cod3f1re.productositalika.R
import com.cod3f1re.productositalika.Utils.ModelPreferencesManager
import com.cod3f1re.productositalika.databinding.ActivityBuyProductsBinding
import com.cod3f1re.productositalika.databinding.ActivityMainBinding

class BuyProductsActivity : AppCompatActivity() {

    /**
     * inicializa el viewbinding
     */
    private var _binding: ActivityBuyProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityBuyProductsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ModelPreferencesManager.with(this)


        val productsSaved: ProductsSelected? = ModelPreferencesManager.get<ProductsSelected>("ProductsSelected")
        if (productsSaved != null) {

        }
    }
}