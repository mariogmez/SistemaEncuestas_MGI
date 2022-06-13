package com.example.sistemaencuestas_mgi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sistemaencuestas_mgi.Administrador.MenuAdminActivity
import com.example.sistemaencuestas_mgi.Api.ServiceBuilder
import com.example.sistemaencuestas_mgi.Api.UserApi
import com.example.sistemaencuestas_mgi.Usuario.MenuUsuarioActivity
import com.example.sistemaencuestas_mgi.Usuario.Usuario
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_entrar_log.setOnClickListener{
            Ingresar()
        }
    }

    fun Ingresar () {
        val request = ServiceBuilder.buildService(UserApi::class.java)
        val call = request.getUsuario(txt_nom_log.text.toString())


        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                val post = response.body()

                if (post != null) {
                    val usuario = Usuario (
                        post.idUsuario,
                        post.pwdUsuario,
                        post.bolAdmin
                    )


                    if (txt_pwd_log.text.toString() == post.pwdUsuario){
                        if (post.bolAdmin == 1){

                            var intent = Intent(applicationContext, MenuAdminActivity::class.java)
                            intent.putExtra("idUsuario", usuario.idUsuario)
                            startActivity(intent)
                        }else{
                            var intent = Intent(applicationContext, MenuUsuarioActivity::class.java)
                            intent.putExtra("idUsuario", usuario.idUsuario)
                            startActivity(intent)
                        }
                    }else{
                        Toast.makeText(applicationContext, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    }

                }
                else {
                    Toast.makeText(applicationContext, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}