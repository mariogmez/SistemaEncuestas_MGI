package com.example.sistemaencuestas_mgi.Administrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaencuestas_mgi.Api.ServiceBuilder
import com.example.sistemaencuestas_mgi.Api.UserApi
import com.example.sistemaencuestas_mgi.Encuestas.Encuesta
import com.example.sistemaencuestas_mgi.Encuestas.Encuesta_usuario
import com.example.sistemaencuestas_mgi.ObjBBDD.Vehiculos
import com.example.sistemaencuestas_mgi.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EstadisticasEncuestaAdmActivity : AppCompatActivity() {

    var encuesta: ArrayList<Encuesta_usuario> = ArrayList()
    var vehiculos:ArrayList<Vehiculos> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas_encuesta_adm)

        val objIntent: Intent = intent
        var idEncuesta: String? = objIntent.getStringExtra("idEncuesta")
        if (idEncuesta != null) {
            cargarListadoEncuesta(idEncuesta)
        }


    }

    fun cargarListadoEncuesta (id:String) {

        val request = ServiceBuilder.buildService(UserApi::class.java)
        val call = request.listaEncuestasUsuarios(id)


        call.enqueue(object : Callback<MutableList<Encuesta_usuario>> {
            override fun onResponse(call: Call<MutableList<Encuesta_usuario>>, response: Response<MutableList<Encuesta_usuario>>) {

                for (post in response.body()!!) {
                    encuesta.add(Encuesta_usuario(post.idUsuario, post.idEncuesta, post.tipoVehiculo, post.tipoCombustion, post.tipoEstrella, post.tipoComentario))
                }
                if (response.isSuccessful){
                    media_tipo_vehiculo(encuesta)
                }
            }
            override fun onFailure(call: Call<MutableList<Encuesta_usuario>>, t: Throwable) {
                Toast.makeText(this@EstadisticasEncuestaAdmActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }


    fun media_tipo_vehiculo(encuesta:ArrayList<Encuesta_usuario>){

        val request = ServiceBuilder.buildService(UserApi::class.java)
        val call = request.listaVehiculos()


        call.enqueue(object : Callback<MutableList<Vehiculos>> {
            override fun onResponse(call: Call<MutableList<Vehiculos>>, response: Response<MutableList<Vehiculos>>) {

                for (post in response.body()!!) {
                    vehiculos.add(Vehiculos(post.idVehiculo, post.nombreVehiculo))
                }
                if (response.isSuccessful){
                    /*
                     * AQUI COMPROBAMOS Y SE HACE LA ESTADISTICA
                     */
                    var sumTipoUno :Int = 0
                    var sumTipoDos :Int = 0
                    var sumTipoTres :Int = 0
                    var sumTipoCuatro :Int = 0

                    for (i in 0 until encuesta.size) {
                        if ( encuesta[i].tipoVehiculo == vehiculos[0].idVehiculo){
                            sumTipoUno += 1
                        }
                        if ( encuesta[i].tipoVehiculo == vehiculos[1].idVehiculo){
                            sumTipoDos += 1
                        }
                        if ( encuesta[i].tipoVehiculo == vehiculos[2].idVehiculo){
                            sumTipoTres += 1
                        }
                        if ( encuesta[i].tipoVehiculo == vehiculos[3].idVehiculo){
                            sumTipoCuatro += 1
                        }
                    }

                    //aqui se lo pasamos a los graficos


                }
            }
            override fun onFailure(call: Call<MutableList<Vehiculos>>, t: Throwable) {
                Toast.makeText(this@EstadisticasEncuestaAdmActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })




    }
}