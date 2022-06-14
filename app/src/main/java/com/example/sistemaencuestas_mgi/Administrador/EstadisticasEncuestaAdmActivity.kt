package com.example.sistemaencuestas_mgi.Administrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.example.sistemaencuestas_mgi.Api.ServiceBuilder
import com.example.sistemaencuestas_mgi.Api.UserApi
import com.example.sistemaencuestas_mgi.Encuestas.Encuesta_usuario
import com.example.sistemaencuestas_mgi.ObjBBDD.Vehiculos
import com.example.sistemaencuestas_mgi.R
import kotlinx.android.synthetic.main.activity_estadisticas_encuesta_adm.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

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
                    media_tipo_combustible(encuesta)
                    media_tipo_estrellas(encuesta)
                }
            }
            override fun onFailure(call: Call<MutableList<Encuesta_usuario>>, t: Throwable) {
                Toast.makeText(this@EstadisticasEncuestaAdmActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun media_tipo_estrellas(encuesta:ArrayList<Encuesta_usuario>){
        var cont = 0
        for (i in 0 until encuesta.size){
            cont += encuesta[i].tipoEstrella!!
        }
        rtStarsGraf.progress = (cont/encuesta.size)

    }

    fun stringToWords(s : String) = s.trim().splitToSequence(',')
        .filter { it.isNotEmpty() }.toList()

    fun media_tipo_combustible(encuesta:ArrayList<Encuesta_usuario>){
        var contDiesel:Double = 0.0
        var contGasolina:Double = 0.0
        var contElectrico:Double = 0.0
        var contHibrido:Double = 0.0

        for (i in 0 until encuesta.size){
            var cad:String = encuesta[i].tipoCombustion.toString()
            if(cad.contains(",")){
                var array:ArrayList<String> = stringToWords(cad) as ArrayList<String>
                for (x in 0 until array.size){
                    if (array[x] == "diesel"){
                        contDiesel += 1
                    }
                    if (array[x] == "gasolina"){
                        contGasolina += 1
                    }
                    if (array[x] == "electrico"){
                        contElectrico += 1
                    }
                    if (array[x] == "hibrido"){
                        contHibrido += 1
                    }
                }
            }else{
                if (cad == "diesel"){
                    contDiesel += 1
                }
                if (cad == "gasolina"){
                    contGasolina += 1
                }
                if (cad == "electrico"){
                    contElectrico += 1
                }
                if (cad == "hibrido"){
                    contHibrido += 1
                }
            }
        }

        var total:Double = contElectrico + contDiesel + contHibrido + contGasolina

        txtGraf6.text = "" + ((contGasolina/total)* 100).roundToInt() +"%"
        txtGraf.text = "" + ((contDiesel/total)* 100).roundToInt() +"%"
        txtGraf5.text = "" + ((contElectrico/total)* 100).roundToInt() +"%"
        txtGraf7.text = "" + ((contHibrido/total)* 100).roundToInt() +"%"

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
                    var sumTipoUno :Double = 0.0
                    var sumTipoDos :Double = 0.0
                    var sumTipoTres :Double = 0.0
                    var sumTipoCuatro :Double = 0.0

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
                    var total:Double = sumTipoUno + sumTipoDos + sumTipoTres + sumTipoCuatro


                    txtGraf1.text = "" + ((sumTipoUno/total)*100).roundToInt() + "%"
                    txtGraf2.text = "" + ((sumTipoDos/total)*100).roundToInt() + "%"
                    txtGraf3.text = "" + ((sumTipoTres/total)*100).roundToInt() + "%"
                    txtGraf4.text = "" + ((sumTipoCuatro/total)*100).roundToInt() + "%"

                }
            }
            override fun onFailure(call: Call<MutableList<Vehiculos>>, t: Throwable) {
                Toast.makeText(this@EstadisticasEncuestaAdmActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })




    }
}