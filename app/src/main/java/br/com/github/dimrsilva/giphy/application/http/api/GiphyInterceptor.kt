package br.com.github.dimrsilva.giphy.application.http.api

import android.provider.Settings
import okhttp3.Interceptor
import okhttp3.Response

class GiphyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequestBuilder = request.newBuilder()
        val newUrlBuilder = request.url().newBuilder()

        newUrlBuilder.addQueryParameter("api_key", API_KEY)
        newUrlBuilder.addQueryParameter("random_id", Settings.Secure.ANDROID_ID)

        newRequestBuilder.url(newUrlBuilder.build())

        return chain.proceed(newRequestBuilder.build())
    }

    companion object {
        private const val API_KEY = "TZPH9Io9fwjaDgAQTlUylv912esVKhPZ"
    }
}