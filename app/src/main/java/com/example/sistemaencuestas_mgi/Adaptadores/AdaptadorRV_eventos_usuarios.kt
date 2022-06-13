package com.example.sistemaencuestas_mgi.Adaptadores

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaencuestas_mgi.Administrador.MenuAdminActivity
import com.example.sistemaencuestas_mgi.Encuestas.Encuesta
import com.example.sistemaencuestas_mgi.Encuestas.EncuestaModelActivity
import com.example.sistemaencuestas_mgi.R
import com.example.sistemaencuestas_mgi.Usuario.Usuario

class AdaptadorRV_eventos_usuarios : RecyclerView.Adapter<AdaptadorRV_eventos_usuarios.ViewHolder>(){

    var encuesta: ArrayList<Encuesta> = ArrayList()
    var idUsuario:String? = null
    lateinit var context: Context

    fun AdaptadorRV_eventos_usuarios(encuesta: ArrayList<Encuesta>, context: Context, idUsuario:String?) {
        this.encuesta = encuesta
        this.context = context
        this.idUsuario = idUsuario
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = encuesta.get(position)


        if(item.estadoEncuesta != 0){
            holder.bind(item, context, this, idUsuario)
        }else{
            holder.itemView.setVisibility(View.GONE);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.rv_target_encuesta_usu,parent, false)
        )
    }


    override fun getItemCount(): Int {
        return encuesta.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nombreEncuesta =  view.findViewById(R.id.txtNombreEncuestaUsu) as TextView


        fun bind(encuesta: Encuesta, context: Context, adapter: AdaptadorRV_eventos_usuarios, idUsuario:String?) {
            nombreEncuesta.text = encuesta.nomEncuesta


            itemView.setOnClickListener(View.OnClickListener {
                if (encuesta.idEncuesta == 1){
                    val intent = Intent(context, EncuestaModelActivity::class.java)
                    intent.putExtra("idUsuario", idUsuario)
                    intent.putExtra("idEncuesta", encuesta.idEncuesta.toString())
                    itemView.context.startActivity(intent)
                }


            })


        }

    }
}