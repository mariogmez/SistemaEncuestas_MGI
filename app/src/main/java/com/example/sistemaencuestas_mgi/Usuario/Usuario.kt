package com.example.sistemaencuestas_mgi.Usuario
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario(@SerializedName("idUsuario")
                   var idUser: String? = null,

                   @SerializedName("pwdUsuario")
                   var idRol: String? = null,

                   @SerializedName("bolAdmin")
                   var pass: Int? = null) : Serializable {
}