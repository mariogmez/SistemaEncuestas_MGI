package com.example.sistemaencuestas_mgi.Api
import com.example.sistemaencuestas_mgi.Usuario.Usuario
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("usuario/{name}")
    fun getUnUsuario(@Path("name") id:String): Call<Usuario>
}