package com.example.sistemaencuestas_mgi.Usuario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaencuestas_mgi.Adaptadores.AdaptadorRV_eventos_administracion
import com.example.sistemaencuestas_mgi.Adaptadores.AdaptadorRV_eventos_usuarios
import com.example.sistemaencuestas_mgi.Api.ServiceBuilder
import com.example.sistemaencuestas_mgi.Api.UserApi
import com.example.sistemaencuestas_mgi.Encuestas.Encuesta
import com.example.sistemaencuestas_mgi.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuUsuarioActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    var encuesta: ArrayList<Encuesta> = ArrayList()
    val mAdapter : AdaptadorRV_eventos_usuarios = AdaptadorRV_eventos_usuarios()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_usuario)


        val objIntent: Intent = intent
        var idUsuario: String? = objIntent.getStringExtra("idUsuario")
        supportActionBar?.title = "Bienvenido " + idUsuario



        cargarListadoEncuesta (idUsuario)
    }

    fun cargarListadoEncuesta (idUsuario:String?) {
        val request = ServiceBuilder.buildService(UserApi::class.java)
        val call = request.listaEncuestas()


        call.enqueue(object : Callback<MutableList<Encuesta>> {
            override fun onResponse(call: Call<MutableList<Encuesta>>, response: Response<MutableList<Encuesta>>) {

                for (post in response.body()!!) {
                    encuesta.add(Encuesta(post.idEncuesta,post.nomEncuesta,post.estadoEncuesta))
                }
                if (response.isSuccessful){
                    recyclerView = findViewById<RecyclerView>(R.id.rv_encuesta_usu)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.layoutManager = LinearLayoutManager(this@MenuUsuarioActivity)
                    mAdapter.AdaptadorRV_eventos_usuarios(encuesta, this@MenuUsuarioActivity, idUsuario)
                    recyclerView.adapter = mAdapter

                }
            }
            override fun onFailure(call: Call<MutableList<Encuesta>>, t: Throwable) {
                Toast.makeText(this@MenuUsuarioActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}