package com.example.sistemaencuestas_mgi.Administrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sistemaencuestas_mgi.Api.ServiceBuilder
import com.example.sistemaencuestas_mgi.Api.UserApi
import com.example.sistemaencuestas_mgi.Encuestas.Encuesta_usuario
import com.example.sistemaencuestas_mgi.R
import kotlinx.android.synthetic.main.activity_analizador_encuestas_adm.*
import kotlinx.android.synthetic.main.activity_estadisticas_encuesta_adm.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnalizadorEncuestasAdmActivity : AppCompatActivity() {
    var encuesta: ArrayList<Encuesta_usuario> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analizador_encuestas_adm)
        supportActionBar?.hide()
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
                    var menu:Int = 0

                    cargarEncuesta(encuesta, menu)
                    btnDerecha.setOnClickListener {
                        menu += 1
                        if (menu < encuesta.size && menu >= 0){
                            cargarEncuesta(encuesta, menu)
                        }else{
                            menu -= 1
                        }
                    }

                    btnIzquierda.setOnClickListener {
                        menu -= 1
                        if (menu < encuesta.size && menu >= 0){
                            cargarEncuesta(encuesta, menu)
                        }else{
                            menu += 1
                        }
                    }



                }
            }
            override fun onFailure(call: Call<MutableList<Encuesta_usuario>>, t: Throwable) {
                Toast.makeText(this@AnalizadorEncuestasAdmActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }



    fun stringToWords(s : String) = s.trim().splitToSequence(',')
        .filter { it.isNotEmpty() }.toList()


    fun cargarEncuesta(encuesta:ArrayList<Encuesta_usuario>, menu:Int){
        rellena_rdbt(encuesta[menu])
        rellena_checks(encuesta[menu])
        rtStars2_adm.progress = encuesta[menu].tipoEstrella!!
        txtResp.text = encuesta[menu].tipoComentario
        textView23.text =  encuesta[menu].idUsuario


    }

    fun vacia_rdbt(){
        radio1_adm.isChecked =  false
        radio2_adm.isChecked =  false
        radio3_adm.isChecked =  false
        radio4_adm.isChecked =  false
    }
    fun rellena_rdbt(encuesta:Encuesta_usuario){
        vacia_rdbt()
        if (encuesta.tipoVehiculo == 1){
            radio1_adm.isChecked =  true
        }
        if (encuesta.tipoVehiculo == 2){
            radio2_adm.isChecked =  true
        }
        if (encuesta.tipoVehiculo == 3){
            radio3_adm.isChecked =  true
        }
        if (encuesta.tipoVehiculo == 4){
            radio4_adm.isChecked =  true
        }
    }

    fun vacia_checks(){
        cbx7_adm.isChecked = false
        cbx6_adm.isChecked = false
        cbx5_adm.isChecked = false
        cbx_adm.isChecked = false
    }
    fun rellena_checks(encuesta:Encuesta_usuario){
        vacia_checks()
        var cad:String = encuesta.tipoCombustion.toString()
        if(cad.contains(",")){
            var array:ArrayList<String> = stringToWords(cad) as ArrayList<String>
            for (x in 0 until array.size){
                if (array[x] == "diesel"){
                    cbx7_adm.isChecked = true
                }
                if (array[x] == "gasolina"){
                    cbx6_adm.isChecked = true
                }
                if (array[x] == "electrico"){
                    cbx5_adm.isChecked = true
                }
                if (array[x] == "hibrido"){
                    cbx_adm.isChecked = true
                }
            }
        }else{
            if (cad == "diesel"){
                cbx7_adm.isChecked = true
            }
            if (cad == "gasolina"){
                cbx6_adm.isChecked = true
            }
            if (cad == "electrico"){
                cbx5_adm.isChecked = true
            }
            if (cad == "hibrido"){
                cbx_adm.isChecked = true
            }
        }
    }
}