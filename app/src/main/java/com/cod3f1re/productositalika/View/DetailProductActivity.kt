package com.cod3f1re.productositalika.View

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.cod3f1re.productositalika.Utils.ModelPreferencesManager
import com.cod3f1re.productositalika.Model.Product
import com.cod3f1re.productositalika.Model.ProductBuy
import com.cod3f1re.productositalika.Model.ProductsSelected
import com.cod3f1re.productositalika.R
import com.cod3f1re.productositalika.Utils.BaseActivity
import com.cod3f1re.productositalika.databinding.ActivityDetailProductBinding

class DetailProductActivity : BaseActivity() {
    /**
     * inicializa el viewbinding
     */
    private var _binding: ActivityDetailProductBinding? = null
    private val binding get() = _binding!!
    private var quantity = 1

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val product = intent.getSerializableExtra("product",) as? Product

        //Se verifica si hay productos en el carrito
        verifyCart()

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

                val productsSaved: ProductsSelected? =
                    ModelPreferencesManager.get<ProductsSelected>("ProductsSelected")

                if(productsSaved!=null){
                    //Se crea un nuevo objeto para mayor control y manipulacion, pero agregandole ahora la cantidad de elementos de ese producto
                    productsSaved?.products?.add(ProductBuy(product.id,product.name,product.price,product.description,product.image,quantity))
                    ModelPreferencesManager.put(productsSaved, "ProductsSelected")
                }else{
                    var productsActually: ArrayList<ProductBuy> = ArrayList()
                    //Se crea un nuevo objeto para mayor control y manipulacion, pero agregandole ahora la cantidad de elementos de ese producto
                    productsActually.add(ProductBuy(product.id,product.name,product.price,product.description,product.image,quantity))
                    val productsSaved = ProductsSelected(productsActually)
                    ModelPreferencesManager.put(productsSaved, "ProductsSelected")
                }

                val intent = Intent(this, BuyProductsActivity::class.java)
                startActivity(intent)

            }

            //Se lleva al carrito de compras
            binding.tvProductsNumber.setOnClickListener {
                openCart()
            }
            binding.imgCart.setOnClickListener {
                openCart()
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
            if(quantity>1){
                quantity--
                binding.quantity.text = "$quantity"
            }
        }

        binding.tvProductsNumber.setOnClickListener {
            openCart()
        }
    }


    override fun onPause() {
        super.onPause()
        //Se verifica si hay productos en el carrito
        verifyCart()
    }

    override fun onResume() {
        super.onResume()
        //Se verifica si hay productos en el carrito
        verifyCart()
    }

    fun verifyCart(){
        if(getProducts()>=1){
            //Se verifica si hay productos en el carrito
            binding.tvProductsNumber.text = "${getProducts()}"
            //Se muestra el carrito de compras
            binding.tvProductsNumber.visibility = View.VISIBLE
        }else{
            //Se oculta el carrito de compras
            binding.tvProductsNumber.visibility = View.GONE
        }
    }

}