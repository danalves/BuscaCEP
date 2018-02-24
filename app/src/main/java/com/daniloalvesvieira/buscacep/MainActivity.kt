package com.daniloalvesvieira.buscacep

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener
import com.daniloalvesvieira.buscacep.api.CEPAPI
import com.daniloalvesvieira.buscacep.model.DadosCEP
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btBuscar.setOnClickListener {
            pesquisarCEP()
        }

        val maskCEP = MaskEditTextChangedListener("#####-###", etCEP)
        // adiciona a máscara no objeto
        etCEP.addTextChangedListener(maskCEP)

    }

    private fun pesquisarCEP() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://viacep.com.br")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(CEPAPI::class.java)

        api.buscarCEP(etCEP.text.toString())
                .enqueue(object : Callback<DadosCEP> {
                    override fun onResponse(call: Call<DadosCEP>?, response: Response<DadosCEP>?) {
                        tvEndereco.text = response?.body()?.logradouro + " - " + response?.body()?.bairro +
                                " - " + response?.body()?.localidade + "/" + response?.body()?.uf
                    }

                    override fun onFailure(call: Call<DadosCEP>?, t: Throwable?) {
                        Toast.makeText(applicationContext, t?.message, Toast.LENGTH_LONG).show()
                    }
                })


    }
}
