package com.example.sistemaencuestas_mgi.Administrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaencuestas_mgi.Adaptadores.AdaptadorRV_usuarios
import com.example.sistemaencuestas_mgi.Api.ServiceBuilder
import com.example.sistemaencuestas_mgi.Api.UserApi
import com.example.sistemaencuestas_mgi.R
import com.example.sistemaencuestas_mgi.Usuario.MenuUsuarioActivity
import com.example.sistemaencuestas_mgi.Usuario.Usuario
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GestionUsuariosAdmActivity : AppCompatActivity() {


    lateinit var recyclerView : RecyclerView
    var usuarios: ArrayList<Usuario> = ArrayList()
    val mAdapter : AdaptadorRV_usuarios = AdaptadorRV_usuarios()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_usuarios_adm)

        cargarListadoUsuarios()

    }

    fun cargarListadoUsuarios () {
        val request = ServiceBuilder.buildService(UserApi::class.java)
        val call = request.getListaUsuarios()


        call.enqueue(object : Callback<MutableList<Usuario>> {
            override fun onResponse(call: Call<MutableList<Usuario>>, response: Response<MutableList<Usuario>>) {

                for (post in response.body()!!) {
                    usuarios.add(Usuario(post.idUsuario,post.pwdUsuario,post.bolAdmin))
                }
                if (response.isSuccessful){
                    recyclerView = findViewById<RecyclerView>(R.id.rv_usu_adm)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.layoutManager = LinearLayoutManager(this@GestionUsuariosAdmActivity)
                    mAdapter.AdaptadorRV_usuarios(usuarios, this@GestionUsuariosAdmActivity)
                    recyclerView.adapter = mAdapter

                }
            }
            override fun onFailure(call: Call<MutableList<Usuario>>, t: Throwable) {
                Toast.makeText(this@GestionUsuariosAdmActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}