package com.example.sistemaencuestas_mgi.ObjBBDD

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Vehiculos(
                    @SerializedName("idVehiculo")
                    var idVehiculo: Int? = null,

                    @SerializedName("nombreVehiculo")
                    var nombreVehiculo: String? = null) : Serializable {
}