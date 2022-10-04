package com.cod3f1re.productositalika.View

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cod3f1re.productositalika.Model.ProductBuy
import com.cod3f1re.productositalika.Model.ProductsSelected
import com.cod3f1re.productositalika.Utils.ModelPreferencesManager
import com.cod3f1re.productositalika.ViewModel.Adapters.ProductBuyAdapter
import com.cod3f1re.productositalika.databinding.ActivityBuyProductsBinding


class BuyProductsActivity : AppCompatActivity() {

    /**
     * inicializa el viewbinding
     */
    private var _binding: ActivityBuyProductsBinding? = null
    private val binding get() = _binding!!
    private var dataList = ArrayList<ProductBuy>()

    private lateinit var productsAdapter: ProductBuyAdapter
    private var quantity: Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityBuyProductsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ModelPreferencesManager.with(this)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val productsSaved: ProductsSelected? = ModelPreferencesManager.get<ProductsSelected>("ProductsSelected")
        if (productsSaved != null) {

            var totalPrice=0
            for (product in productsSaved.products){
                totalPrice += product.price * product.count
                dataList.add(product)
                quantity+=product.count
            }
            binding.tvProductsCount.text = " ${quantity}"
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
            }
        }

        binding.btnComprar.setOnClickListener {
            if(validateFields()){
                Toast.makeText(this, "Compra Exitosa", Toast.LENGTH_SHORT).show()
                val preferences = getSharedPreferences("PREFERENCES_ITALIKA", 0)
                preferences.edit().remove("ProductsSelected").apply()
                finish()
            }
        }
    }

    fun validateFields(): Boolean{
        binding.ilEmail.error = null
        binding.ilPhone.error = null
        var complete=true
        if(binding.etEmail.text?.toString() == "" && binding.etEmail.text?.toString()!!.length<=7){
            binding.ilEmail.error = "Debes de ingresar un correo electronico valido"
            complete=false
        }
        if(binding.etPhone.text?.toString() == "" && binding.etPhone.text?.toString()!!.length<=9){
            binding.ilPhone.error = "Debes de ingresar un numero celular valido de 10 digitos"
            complete=false
        }
        return complete
    }

}