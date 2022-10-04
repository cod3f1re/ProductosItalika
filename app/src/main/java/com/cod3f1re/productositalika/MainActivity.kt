package com.cod3f1re.productositalika

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cod3f1re.productositalika.Model.Product
import com.cod3f1re.productositalika.Utils.BaseActivity
import com.cod3f1re.productositalika.View.DetailProductActivity
import com.cod3f1re.productositalika.ViewModel.Adapters.ProductAdapter
import com.cod3f1re.productositalika.databinding.ActivityMainBinding
import java.io.Serializable


class MainActivity : BaseActivity(){

    /**
     * inicializa el viewbinding
     */
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var dataList = ArrayList<Product>()

    private lateinit var programsAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Se verifica si hay productos en el carrito
        binding.tvProductsNumber.text = "${getProducts()}"

        binding.tvProductsNumber.setOnClickListener {
            openCart()
        }

        // Se crea el recyclerview en grid
        binding.rvProducts.layoutManager = GridLayoutManager(this,2)

        // Bucle para crear varios productos de italika
        for (i in 1..6) {
            dataList.add(
                Product(23,"Motocicleta de trabajo, Motocicleta Italika $i AT 125 RT",19000,
                "Tipo de motor\t4 Tiempos, monocilíndrico\n" +
                        "Cilindrada\t124 CC\n" +
                        "Velocidad máxima\t85 km/h\n" +
                        "Potencia máxima\t8.7 Hp @ 7700 RPM\n" +
                        "Torque máximo\t9 N-m @ 7000 RPM\n" +
                        "Rendimiento de combustible\t36 km/L\n" +
                        "Sistema de arranque\tEléctrico y de pedal\n" +
                        "Sistema de ignición\tCDI (Ignición por descarga de condensador)\n" +
                        "Transmisión Final\tSemi-Automática de 4 velocidades/por cadena\n" +
                        "Capacidad de combustible\t3.5 L\n" +
                        "Rendimiento de combustible por tanque\t126 km\n" +
                        "Capacidad de aceite\t.9 L\n" +
                        "Tipo de enfriamiento\tAire forzado","https://chedrauimx.vtexassets.com/arquivos/ids/7427955/7503031090192_03.jpg?v=637992117580700000")
            )
        }

        // Bucle para crear varios productos de italika
        for (i in 7..15) {
            dataList.add(
                Product(23,"Motocicleta de trabajo, Motocicleta Italika $i D125 LT",23000,
                "Tipo de motor\t4 Tiempos, monocilíndrico\n" +
                        "Cilindrada\t124 CC\n" +
                        "Velocidad máxima\t71 km/h\n" +
                        "Potencia máxima\t7.6 Hp @ 7500 RPM\n" +
                        "Torque máximo\t6.9 N-m @ 6500 RPM\n" +
                        "Rendimiento de combustible\t29 km/L\n" +
                        "Sistema de arranque\tEléctrico y de pedal\n" +
                        "Sistema de ignición\tCDI (Ignición por descarga de condensador)\n" +
                        "Transmisión Final\tAutomática/por banda\n" +
                        "Capacidad de combustible\t5.5 L\n" +
                        "Rendimiento de combustible por tanque\t159.5 km\n" +
                        "Capacidad de aceite\t.8 L\n" +
                        "Tipo de enfriamiento\tAire forzado","https://www.bicimotos.com.mx/pub/media/catalog/product/cache/1a0ccf9d37918f21e66e749b62550d57/-/a/-a4125210149p-02-1200wx1200h.jpg")
            )
        }

        // Bucle para crear varios productos de italika
        for (i in 16..23) {
            dataList.add(
                Product(23,"Motocicleta de estilo deportivo, Motocicleta Italika $i",57000,
                "Tipo de motor\t4 Tiempos, monocilíndrico OHC\n" +
                        "Cilindrada\t250 CC\n" +
                        "Velocidad máxima\t123 km/h\n" +
                        "Potencia máxima\t17.9 Hp @ 7000 RPM\n" +
                        "Torque máximo\t17.7 N-m @ 6000 RPM\n" +
                        "Rendimiento de combustible\t23.5 km/L\n" +
                        "Sistema de arranque\tEléctrico\n" +
                        "Transmisión Final\tEstándar de 6 velocidades/por cadena\n" +
                        "Capacidad de combustible\t11 L\n" +
                        "Rendimiento de combustible por tanque\t258.5 km\n" +
                        "Capacidad de aceite\t1.3 L","https://www.italika.mx/WebVisorArchivosITK/Archivo.aspx?Tipo=3&Archivo=WebPortalMexicoITK/img/Modelos/LineaZ/Maxis/250Z.png")
            )
        }

        // Se establece el adaptador y datos para el rv
        programsAdapter = ProductAdapter(dataList)
        programsAdapter.submitList(dataList)
        binding.rvProducts.adapter = programsAdapter

        //Le damos la funcion de clic a los productos
        programsAdapter.onItemClick = { product ->
            Log.d("TAG", product.name)
            val intent = Intent(this, DetailProductActivity::class.java)
            intent.putExtra("product", product as Serializable)
            startActivity(intent)
        }

        //Se deja un listener, para captar cada cambio de caracter en la barra de busqueda
        binding.searchProducts.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterList(newText)
                }
                return true
            }

        })

    }

    override fun onPause() {
        super.onPause()
        //Se verifica si hay productos en el carrito
        binding.tvProductsNumber.text = "${getProducts()}"
    }

    override fun onResume() {
        super.onResume()
        //Se verifica si hay productos en el carrito
        binding.tvProductsNumber.text = "${getProducts()}"
    }

    /**
     * Metodo para cambiar los datos dell rv mostrado
     */
    fun filterList(search:String){
        val filteredList= ArrayList<Product>()
        for ( product in dataList){
            //Se convierte a minuscula, para que siempre se comparen las letras en minuscula y no distina entre las mayusculas
            if (product.name.lowercase().contains(search.lowercase())){
                filteredList.add(product)
            }
        }
        //Si la lista esta vacia, se oculta el rv
        if(filteredList.isEmpty()){
            Toast.makeText(this,"No hay coincidencias",Toast.LENGTH_SHORT).show()
            binding.rvProducts.visibility = View.INVISIBLE
        }else{
            //Se vuelven a establecer el adapter, pero ahora con los nuevos valores
            programsAdapter.submitList(filteredList)

            // Setting the Adapter with the recyclerview
            binding.rvProducts.adapter = programsAdapter

            //Se actualiza el recyclerview para actualizar todos los datos
            programsAdapter.notifyDataSetChanged()

            //Si la lista tiene elementos, se muestra el rv
            binding.rvProducts.visibility = View.VISIBLE
        }

    }


}