package com.example.sistemaencuestas_mgi.Encuestas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Encuesta_usuario(
                    @SerializedName("idUsuario")
                    var idUsuario: String? = null,

                    @SerializedName("idEncuesta")
                    var idEncuesta: Int? = null,

                    @SerializedName("tipoVehiculo")
                    var tipoVehiculo: Int? = null,

                    @SerializedName("tipoCombustion")
                    var tipoCombustion: String? = null,

                    @SerializedName("tipoEstrella")
                    var tipoEstrella: Int? = null,

                    @SerializedName("tipoComentario")
                    var tipoComentario: String? = null) : Serializable {
}