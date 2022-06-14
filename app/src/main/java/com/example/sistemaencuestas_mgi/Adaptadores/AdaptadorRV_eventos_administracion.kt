package com.example.sistemaencuestas_mgi.Adaptadores

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaencuestas_mgi.Administrador.AnalizadorEncuestasAdmActivity
import com.example.sistemaencuestas_mgi.Administrador.EstadisticasEncuestaAdmActivity
import com.example.sistemaencuestas_mgi.Api.ServiceBuilder
import com.example.sistemaencuestas_mgi.Api.UserApi
import com.example.sistemaencuestas_mgi.Encuestas.Encuesta
import com.example.sistemaencuestas_mgi.Encuestas.EncuestaModelActivity
import com.example.sistemaencuestas_mgi.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdaptadorRV_eventos_administracion : RecyclerView.Adapter<AdaptadorRV_eventos_administracion.ViewHolder>(){

    var encuesta: ArrayList<Encuesta> = ArrayList()
    lateinit var context: Context

    fun AdaptadorRV_eventos_administracion(encuesta: ArrayList<Encuesta>, context: Context) {
        this.encuesta = encuesta
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = encuesta.get(position)
        holder.bind(item, context, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.rv_target_adm_encuesta,parent, false)
        )
    }


    override fun getItemCount(): Int {
        return encuesta.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreEncuesta =  view.findViewById(R.id.txtNombreEncuesta) as TextView
        val pill = view.findViewById(R.id.pill_encuesta_gest) as Switch
        var btn = view.findViewById(R.id.btnResumenTar) as Button
        var btnDelete = view.findViewById(R.id.btnReset) as Button



        fun bind(encuesta: Encuesta, context: Context, adapter: AdaptadorRV_eventos_administracion) {
            nombreEncuesta.text = encuesta.nomEncuesta
            pill.isChecked = encuesta.estadoEncuesta == 1

            itemView.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, AnalizadorEncuestasAdmActivity::class.java)
                intent.putExtra("idEncuesta", encuesta.idEncuesta.toString())
                itemView.context.startActivity(intent)
            })

            btn.setOnClickListener {
                val intent = Intent(context, EstadisticasEncuestaAdmActivity::class.java)
                intent.putExtra("idEncuesta", encuesta.idEncuesta.toString())
                itemView.context.startActivity(intent)
            }

            btnDelete.setOnClickListener {
                Toast.makeText(context, "sisis simba", Toast.LENGTH_SHORT).show()
                var id:Int = encuesta.idEncuesta!!
                borrarPorID(id, context)


            }


            pill.setOnClickListener {
                if (pill.isChecked){


                    val request = ServiceBuilder.buildService(UserApi::class.java)
                    val call = request.activarEncuesta(encuesta)
                    call.enqueue(object : Callback<ResponseBody> {

                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.code() == 200) {
                                Toast.makeText(context, "Estado de la encuesta modificada", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(context, "${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })


                }else{

                    val request = ServiceBuilder.buildService(UserApi::class.java)
                    val call = request.desactivarEncuesta(encuesta)
                    call.enqueue(object : Callback<ResponseBody> {

                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.code() == 200) {
                                Toast.makeText(context, "Estado de la encuesta modificada", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(context, "${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })

                }
            }


        }

        private fun borrarPorID(id:Int, context: Context) {
            val request = ServiceBuilder.buildService(UserApi::class.java)
            val call = request.delEncuesta(id)
            call.enqueue(object : Callback<ResponseBody> {

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                    if (response.isSuccessful){ //Esto es otra forma de hacerlo en lugar de mirar el código.
                        Toast.makeText(context, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Fernando","Algo ha fallado en la conexión.")
                    Toast.makeText(context, "Algo ha fallado en la conexión.",Toast.LENGTH_LONG).show()
                }
            })
        }

    }

}