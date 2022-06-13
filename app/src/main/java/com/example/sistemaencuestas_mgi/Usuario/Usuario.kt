package com.example.sistemaencuestas_mgi.Usuario
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario(@SerializedName("idUsuario")
                   var idUsuario: String? = null,

                   @SerializedName("pwdUsuario")
                   var pwdUsuario: String? = null,

                   @SerializedName("bolAdmin")
                   var bolAdmin: Int? = null) : Serializable {
}