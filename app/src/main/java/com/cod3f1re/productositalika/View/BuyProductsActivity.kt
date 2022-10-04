package com.cod3f1re.productositalika.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import com.cod3f1re.productositalika.Model.Product
import com.cod3f1re.productositalika.Model.ProductBuy
import com.cod3f1re.productositalika.Model.ProductsSelected
import com.cod3f1re.productositalika.R
import com.cod3f1re.productositalika.Utils.ModelPreferencesManager
import com.cod3f1re.productositalika.ViewModel.Adapters.ProductAdapter
import com.cod3f1re.productositalika.ViewModel.Adapters.ProductBuyAdapter
import com.cod3f1re.productositalika.databinding.ActivityBuyProductsBinding
import com.cod3f1re.productositalika.databinding.ActivityMainBinding
import java.io.Serializable

class BuyProductsActivity : AppCompatActivity() {

    /**
     * inicializa el viewbinding
     */
    private var _binding: ActivityBuyProductsBinding? = null
    private val binding get() = _binding!!
    private var dataList = ArrayList<ProductBuy>()

    private lateinit var productsAdapter: ProductBuyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityBuyProductsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ModelPreferencesManager.with(this)


        val productsSaved: ProductsSelected? = ModelPreferencesManager.get<ProductsSelected>("ProductsSelected")
        if (productsSaved != null) {

            var totalPrice=0
            for (product in productsSaved.products){
                totalPrice=+product.price
                dataList.add(product)
            }
            binding.tvTotal.text = "$ ${totalPrice}"


            // Se establece el adaptador y datos para el rv
            binding.rvProductsBuy.layoutManager = LinearLayoutManager(this)
            productsAdapter = ProductBuyAdapter(dataList)
            productsAdapter.submitList(dataList)
            // Setting the Adapter with the recyclerview
            binding.rvProductsBuy.adapter = productsAdapter

            //Le damos la funcion de clic a los productos
            productsAdapter.onItemClick = { product ->
                Log.d("TAG", product.name)
                val intent = Intent(this, DetailProductActivity::class.java)
                intent.putExtra("product", product as Serializable)
                startActivity(intent)
            }


        }
    }

    fun validateFields(): Boolean{
        binding.ilEmail.error = null
        binding.ilPhone.error = null
        var complete=true
        if(binding.etEmail.text?.toString() == "" && binding.etEmail.text?.toString()!!.length>=7){
            binding.ilEmail.error = "Debes de ingresar un correo electronico valido"
            complete=false
        }
        if(binding.etPhone.text?.toString() == "" && binding.etPhone.text?.toString()!!.length>=10){
            binding.ilPhone.error = "Debes de ingresar un numero celular valido de 10 digitos"
            complete=false
        }
        return complete
    }

}