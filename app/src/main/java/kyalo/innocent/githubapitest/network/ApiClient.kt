package kyalo.innocent.githubapitest.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val BASE_URL = "https://api.github.com"

    private var retrofit: Retrofit? = null

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    val apiClient: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }

}