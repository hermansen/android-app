package se.swosch.jackson.http

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import se.swosch.jackson.http.models.ChuckResponse


interface ChuckClient {

    @GET("jokes/random")
    suspend fun random(): ChuckResponse

    companion object {
        val instance: ChuckClient by lazy {
            val mapper = JsonMapper.builder()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io")
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(OkHttpClient.Builder().build())
                .build()

            retrofit.create(ChuckClient::class.java)
        }
    }
}