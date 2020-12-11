package se.sl.androidapp.http

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import se.sl.androidapp.http.models.ChuckResponse


interface ChuckClient {

    @GET("jokes/random")
    suspend fun random(): ChuckResponse

    companion object {
        val instance: ChuckClient by lazy {
            val mapper = JsonMapper.builder()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build()

            val loggingInterceptors = HttpLoggingInterceptor()
            loggingInterceptors.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptors)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io")
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(okHttpClient)
                .build()

            retrofit.create(ChuckClient::class.java)
        }
    }
}