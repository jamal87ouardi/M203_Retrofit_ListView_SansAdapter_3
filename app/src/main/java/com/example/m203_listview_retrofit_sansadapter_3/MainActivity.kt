package com.example.m203_listview_retrofit_sansadapter_3

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getData()
    }

    fun getData() {
        val listView = findViewById<ListView>(R.id.lv)



        // Configurer Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://apiyes.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Créer une instance de l'interface ApiService
        val apiService = retrofit.create(ApiService::class.java)

        // Faire un appel réseau pour récupérer les données
        val call = apiService.getCars()
        call.enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if (response.isSuccessful) {
                    val cars = response.body() ?: emptyList()

                    // Extraire les noms des smartphones en utilisant une boucle for
                    val carNames = mutableListOf<String>()
                    for (c in cars) {
                        carNames.add(c.name+" - "+c.price+ " MAD")
                    }

                    // Utiliser un ArrayAdapter avec simple_list_item_1
                    val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, carNames)
                    listView.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, "Erreur lors de la récupération des données", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Échec de la connexion à l'API", Toast.LENGTH_SHORT).show()
            }
        })


    }

}