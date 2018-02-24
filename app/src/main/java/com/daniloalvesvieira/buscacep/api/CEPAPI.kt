package com.daniloalvesvieira.buscacep.api

import com.daniloalvesvieira.buscacep.model.DadosCEP
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CEPAPI {
    @GET("/ws/{cep}/json/")
    fun buscarCEP(@Path("cep") cepDigitado: String): Call<DadosCEP>


}