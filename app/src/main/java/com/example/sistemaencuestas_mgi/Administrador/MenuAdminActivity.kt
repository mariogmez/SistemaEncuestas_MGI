package com.example.sistemaencuestas_mgi.Administrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sistemaencuestas_mgi.R
import kotlinx.android.synthetic.main.activity_menu_admin.*

class MenuAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_admin)


        val objIntent: Intent = intent
        var idUsuario: String? = objIntent.getStringExtra("idUsuario")
        supportActionBar?.title = "Bienvenido " + idUsuario



        btn_gest_encuestas_adm.setOnClickListener {
            var intent = Intent(applicationContext, GestionEncuestasAdmActivity::class.java)
            startActivity(intent)
        }

        btn_gest_usuarios_adm.setOnClickListener {
            var intent = Intent(applicationContext, GestionUsuariosAdmActivity::class.java)
            startActivity(intent)
        }
    }
}