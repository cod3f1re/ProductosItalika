package com.cod3f1re.productositalika

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.cod3f1re.productositalika.databinding.ActivityDetailProductBinding
import com.cod3f1re.productositalika.databinding.ActivityMainBinding

class DetailProductActivity : AppCompatActivity() {
    /**
     * inicializa el viewbinding
     */
    private var _binding: ActivityDetailProductBinding? = null
    private val binding get() = _binding!!
    private var quantity = 0

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val product = intent.getSerializableExtra("product",) as? Product

        if (product != null) {
            binding.name.text = product.name
            if(product.image.isNotEmpty()){
                //Se establece una imagen desde internet
                Glide
                    .with(this)
                    .load(product.image)
                    .centerCrop()
                    .placeholder(R.drawable.load)
                    .into(binding.image)
            }
            binding.price.text = "Precio: $ ${product.price}"
            binding.description.text = product.description

            binding.btnComprar.setOnClickListener{
                ModelPreferencesManager.with(this)

                val productsSaved: ProductsSelected? = ModelPreferencesManager.get<ProductsSelected>("ProductsSelected")

                if(productsSaved!=null){
                    productsSaved?.products?.add(product)
                    ModelPreferencesManager.put(productsSaved, "ProductsSelected")
                }else{
                    var productsActually: ArrayList<Product> = ArrayList()
                    productsActually.add(product)
                    val productsSaved = ProductsSelected(productsActually)
                    ModelPreferencesManager.put(productsSaved,"ProductsSelected")
                }

            }






        }

        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.more.setOnClickListener {
                quantity++
                binding.quantity.text = "$quantity"
        }

        binding.less.setOnClickListener {
            binding.quantity.text = "$quantity"
            if(quantity>0){
                quantity--
                binding.quantity.text = "$quantity"
            }
        }





    }
}