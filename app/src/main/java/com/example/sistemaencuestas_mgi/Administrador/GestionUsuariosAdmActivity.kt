package com.example.sistemaencuestas_mgi.Administrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaencuestas_mgi.Adaptadores.AdaptadorRV_usuarios
import com.example.sistemaencuestas_mgi.Api.ServiceBuilder
import com.example.sistemaencuestas_mgi.Api.UserApi
import com.example.sistemaencuestas_mgi.R
import com.example.sistemaencuestas_mgi.Usuario.MenuUsuarioActivity
import com.example.sistemaencuestas_mgi.Usuario.Usuario
import kotlinx.android.synthetic.main.activity_dialog.view.*
import kotlinx.android.synthetic.main.activity_gestion_usuarios_adm.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
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

        // MUESTRA UN DIALOG MODIFICADO PARA AÑADIR NUEVOS EVENTOS
        btnAniadirUsu.setOnClickListener{

            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.activity_dialog,null)

            builder.setView(view) // <- se le pasa la vista creada al builder
            val dialog = builder.create() //<- se crea el dialog
            dialog.show() //<- se muestra el showdialog

            view.btnConfirmarDLG.setOnClickListener {
                val cajaNombreSWD = view.txtNombreDLG
                val cajaPwdSWD = view.txtPwdDLG

                val us = Usuario(
                    cajaNombreSWD.text.toString(),
                    cajaPwdSWD.text.toString(),
                    0
                )

                val request = ServiceBuilder.buildService(UserApi::class.java)
                val call = request.insertarUsuario(us)

                call.enqueue(object : Callback<ResponseBody> {

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {}

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    }
                })

                val intent = Intent(this, GestionUsuariosAdmActivity::class.java)
                startActivity(intent)

            }

        }

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