package br.com.github.dimrsilva.giphy.infrastructure

import br.com.github.dimrsilva.giphy.infrastructure.http.GiphyInterceptor
import com.danikula.videocache.HttpProxyCacheServer
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val httpModule = module {
    factory {
        OkHttpClient.Builder()
            .addInterceptor(GiphyInterceptor())
            .build()
    }
    factory {
        Retrofit.Builder()
            .baseUrl("https://api.giphy.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
    }
    single { HttpProxyCacheServer(get()) }
}

val infrastructureModules = listOf(httpModule)