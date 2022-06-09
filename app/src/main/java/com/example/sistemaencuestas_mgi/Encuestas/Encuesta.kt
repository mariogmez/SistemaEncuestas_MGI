package com.example.sistemaencuestas_mgi.Encuestas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Encuesta(@SerializedName("idEncuesta")
                   var idEncuesta: Int? = null,

                   @SerializedName("nomEncuesta")
                   var nomEncuesta: String? = null,

                   @SerializedName("estadoEncuesta")
                   var estadoEncuesta: Int? = null) : Serializable {
}