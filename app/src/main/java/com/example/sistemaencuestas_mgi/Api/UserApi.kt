package com.example.sistemaencuestas_mgi.Api
import com.example.sistemaencuestas_mgi.Encuestas.Encuesta
import com.example.sistemaencuestas_mgi.Encuestas.Encuesta_usuario
import com.example.sistemaencuestas_mgi.ObjBBDD.Vehiculos
import com.example.sistemaencuestas_mgi.Usuario.Usuario
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    @GET("usuarios/{name}")
    fun getUsuario(@Path("name") id:String): Call<Usuario>

    @GET("listaUsuarios")
    fun getListaUsuarios(): Call<MutableList<Usuario>>

    @GET("listaEncuestas")
    fun listaEncuestas(): Call<MutableList<Encuesta>>

    @GET("listaVehiculos")
    fun listaVehiculos(): Call<MutableList<Vehiculos>>

    @GET("listaEncuestasUsuarios/{name}")
    fun listaEncuestasUsuarios(@Path("name") id:String): Call<MutableList<Encuesta_usuario>>

    @Headers("Content-Type:application/json")
    @PUT("activarEncuesta")
    fun activarEncuesta(@Body info: Encuesta) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("desactivarEncuesta")
    fun desactivarEncuesta(@Body info: Encuesta) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("insertarUsuario")
    fun insertarUsuario(@Body info: Usuario) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("insertarRespuesta")
    fun insertarRespuesta(@Body info: Encuesta_usuario) : Call<ResponseBody>

    @GET("encuestas_usuarios/{name}")
    fun comprobarEncuesta(@Path("name") id:String): Call<Encuesta_usuario>

    @Headers("Content-Type:application/json")
    @PUT("modificarEncuesta")
    fun modificarEncuesta(@Body info: Encuesta_usuario) : Call<ResponseBody>

}