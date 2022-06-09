package com.example.sistemaencuestas_mgi.Adaptadores

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaencuestas_mgi.R
import com.example.sistemaencuestas_mgi.Usuario.Usuario

class AdaptadorRV_usuarios : RecyclerView.Adapter<AdaptadorRV_usuarios.ViewHolder>(){

    var usuario: ArrayList<Usuario> = ArrayList()
    lateinit var context: Context

    fun AdaptadorRV_usuarios(usuario: ArrayList<Usuario>, context: Context) {
        this.usuario = usuario
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = usuario.get(position)
        holder.bind(item, context, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.rv_target_adm_usuarios,parent, false)
        )
    }


    override fun getItemCount(): Int {
        return usuario.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreUsuario =  view.findViewById(R.id.txt_nom_usu_adm) as TextView
        val pwdUsuario = view.findViewById(R.id.txt_pwd_usu_adm) as TextView

        fun bind(usuario: Usuario, context: Context, adapter: AdaptadorRV_usuarios) {
            nombreUsuario.text = usuario.idUsuario
            pwdUsuario.text = usuario.pwdUsuario
        }

    }
}