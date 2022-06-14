package com.example.sistemaencuestas_mgi.Encuestas

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.sistemaencuestas_mgi.Api.ServiceBuilder
import com.example.sistemaencuestas_mgi.Api.UserApi
import com.example.sistemaencuestas_mgi.R
import kotlinx.android.synthetic.main.activity_encuesta_model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EncuestaModelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encuesta_model)
        supportActionBar?.hide()

        val objIntent: Intent = intent
        var idUsuario: String? = objIntent.getStringExtra("idUsuario")
        var idEncuesta: String? = objIntent.getStringExtra("idEncuesta")
        var listCombustibles:ArrayList<String> = arrayListOf()

        btnEnviar.setOnClickListener {
            var tipoVehiculo:Int = comprobar_rbtn()
            var listaCombustibles:String = comprobar_chx(listCombustibles)
            var rtEstrella:Int = rtStars.progress
            var comentario:String = txtComentario.text.toString()

            val enc = Encuesta_usuario(idUsuario, idEncuesta.toString().toInt(), tipoVehiculo, listaCombustibles, rtEstrella, comentario)
            if (tipoVehiculo != 0 && listaCombustibles != ""){
                if (idUsuario != null) {
                    compBBDD(enc, idUsuario)
                }
            }else{
                val builder = AlertDialog.Builder(this@EncuestaModelActivity)
                builder.setTitle("Alerta")
                builder.setMessage("Los campos tipo de vehiculo o tipo de combustible estan sin rellenar.")
                builder.setPositiveButton("Aceptar",{ dialogInterface: DialogInterface, i: Int -> })
                builder.show()
            }


        }
    }

    fun compBBDD (enc:Encuesta_usuario, id:String) {

        val request = ServiceBuilder.buildService(UserApi::class.java)
        val call = request.comprobarEncuesta(id)

        call.enqueue(object : Callback<Encuesta_usuario> {
            override fun onResponse(call: Call<Encuesta_usuario>, response: Response<Encuesta_usuario>) {
                val post = response.body()

                if (post != null) {

                    val builder = AlertDialog.Builder(this@EncuestaModelActivity)
                    builder.setTitle("Alerta")
                    builder.setMessage("Ya tiene una encuesta realizada. ¿Desea sobreescribirla?")
                    builder.setPositiveButton("Si",{ dialogInterface: DialogInterface, i: Int ->
                        sobreEscribirEncuesta(enc)
                        finish()
                        startActivity(getIntent())
                    })

                    builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int ->
                        finish()
                        startActivity(getIntent())
                    })
                    builder.show()

                }else{
                    insertarEnBBDD(enc)
                    finish()
                    startActivity(getIntent())
                }
            }
            override fun onFailure(call: Call<Encuesta_usuario>, t: Throwable) {
                Toast.makeText(this@EncuestaModelActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }


    fun sobreEscribirEncuesta(enc:Encuesta_usuario){
        val request = ServiceBuilder.buildService(UserApi::class.java)
        val call = request.modificarEncuesta(enc)
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {}
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }

    fun insertarEnBBDD(enc:Encuesta_usuario){
        val request = ServiceBuilder.buildService(UserApi::class.java)
        val call = request.insertarRespuesta(enc)

        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {}

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }

    fun comprobar_chx(lst:ArrayList<String>): String{
        if (cbx1.isChecked) {
            lst.add("gasolina")
        }

        if (cbx2.isChecked) {
            lst.add("diesel")
        }

        if (cbx3.isChecked) {
            lst.add("electrico")
        }

        if (cbx4.isChecked) {
            lst.add("hibrido")
        }

        var list:String = lst.joinToString(separator = ",")
        return list
    }

    fun comprobar_rbtn(): Int {
        var tipo = 0
        when (rbtGroup_adm.checkedRadioButtonId) {
            R.id.radio1_adm -> tipo = 1
            R.id.radio2_adm -> tipo = 2
            R.id.radio3_adm -> tipo = 3
            R.id.radio4_adm -> tipo = 4
        }
        return tipo
    }



}