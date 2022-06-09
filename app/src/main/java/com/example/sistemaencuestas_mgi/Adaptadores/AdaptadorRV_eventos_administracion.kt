package com.example.sistemaencuestas_mgi.Adaptadores

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaencuestas_mgi.Encuestas.Encuesta
import com.example.sistemaencuestas_mgi.R


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


        fun bind(encuesta: Encuesta, context: Context, adapter: AdaptadorRV_eventos_administracion) {
            nombreEncuesta.text = encuesta.nomEncuesta
            pill.isChecked = encuesta.estadoEncuesta == 1

            itemView.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, ""+encuesta.estadoEncuesta, Toast.LENGTH_SHORT).show()
            })


            pill.setOnClickListener {
                if (pill.isChecked){

                }else{

                }
            }


        }

    }
}