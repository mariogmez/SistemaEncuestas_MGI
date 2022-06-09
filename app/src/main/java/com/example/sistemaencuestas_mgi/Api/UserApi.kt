package com.example.sistemaencuestas_mgi.Api
import com.example.sistemaencuestas_mgi.Encuestas.Encuesta
import com.example.sistemaencuestas_mgi.Usuario.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {

    @GET("usuarios/{name}")
    fun getUsuario(@Path("name") id:String): Call<Usuario>

    @GET("listaUsuarios")
    fun getListaUsuarios(): Call<MutableList<Usuario>>

    @GET("listaEncuestas")
    fun listaEncuestas(): Call<MutableList<Encuesta>>


}