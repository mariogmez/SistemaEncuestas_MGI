package com.example.sistemaencuestas_mgi.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaencuestas_mgi.Encuestas.Encuesta
import com.example.sistemaencuestas_mgi.R
import com.example.sistemaencuestas_mgi.Usuario.Usuario

class AdaptadorRV_eventos_usuarios : RecyclerView.Adapter<AdaptadorRV_eventos_usuarios.ViewHolder>(){

    var encuesta: ArrayList<Encuesta> = ArrayList()
    lateinit var context: Context

    fun AdaptadorRV_eventos_usuarios(encuesta: ArrayList<Encuesta>, context: Context) {
        this.encuesta = encuesta
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = encuesta.get(position)
        holder.bind(item, context, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.rv_target_adm_usuarios,parent, false)
        )
    }


    override fun getItemCount(): Int {
        return encuesta.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bind(encuesta: Encuesta, context: Context, adapter: AdaptadorRV_eventos_usuarios) {

        }

    }
}