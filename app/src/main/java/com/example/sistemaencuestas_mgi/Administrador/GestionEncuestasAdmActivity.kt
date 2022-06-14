package com.example.sistemaencuestas_mgi.Administrador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaencuestas_mgi.Adaptadores.AdaptadorRV_eventos_administracion
import com.example.sistemaencuestas_mgi.Adaptadores.AdaptadorRV_usuarios
import com.example.sistemaencuestas_mgi.Api.ServiceBuilder
import com.example.sistemaencuestas_mgi.Api.UserApi
import com.example.sistemaencuestas_mgi.Encuestas.Encuesta
import com.example.sistemaencuestas_mgi.R
import com.example.sistemaencuestas_mgi.Usuario.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GestionEncuestasAdmActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    var encuesta: ArrayList<Encuesta> = ArrayList()
    val mAdapter : AdaptadorRV_eventos_administracion = AdaptadorRV_eventos_administracion()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_encuestas_adm)
        cargarListadoEncuesta()
        supportActionBar?.title = "Gestion de las encuestas"
    }

    fun cargarListadoEncuesta () {
        val request = ServiceBuilder.buildService(UserApi::class.java)
        val call = request.listaEncuestas()


        call.enqueue(object : Callback<MutableList<Encuesta>> {
            override fun onResponse(call: Call<MutableList<Encuesta>>, response: Response<MutableList<Encuesta>>) {

                for (post in response.body()!!) {
                    encuesta.add(Encuesta(post.idEncuesta,post.nomEncuesta,post.estadoEncuesta))
                }
                if (response.isSuccessful){
                    recyclerView = findViewById<RecyclerView>(R.id.rv_encuesta_adm)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.layoutManager = LinearLayoutManager(this@GestionEncuestasAdmActivity)
                    mAdapter.AdaptadorRV_eventos_administracion(encuesta, this@GestionEncuestasAdmActivity)
                    recyclerView.adapter = mAdapter

                }
            }
            override fun onFailure(call: Call<MutableList<Encuesta>>, t: Throwable) {
                Toast.makeText(this@GestionEncuestasAdmActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}