package br.com.github.dimrsilva.giphy.application

import br.com.github.dimrsilva.giphy.application.http.TrendingGifsEndpoint
import br.com.github.dimrsilva.giphy.application.http.api.GiphyApi
import br.com.github.dimrsilva.giphy.application.http.api.GiphyInterceptor
import br.com.github.dimrsilva.giphy.application.usecase.LoadTrendingGifsUseCase
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

    factory { get<Retrofit>().create(GiphyApi::class.java) }

    factory { TrendingGifsEndpoint(get()) }
}

private val useCaseModule = module {
    factory { LoadTrendingGifsUseCase(get()) }
}

val applicationModules = listOf(httpModule, useCaseModule)