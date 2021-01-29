package br.com.github.dimrsilva.giphy.application

import br.com.github.dimrsilva.giphy.application.http.TrendingGifsEndpoint
import br.com.github.dimrsilva.giphy.application.http.api.GiphyApi
import br.com.github.dimrsilva.giphy.application.http.api.GiphyResultPayloadMapper
import br.com.github.dimrsilva.giphy.application.usecase.LoadTrendingGifsUseCase
import org.koin.dsl.module
import retrofit2.Retrofit

private val httpModule = module {
    factory { get<Retrofit>().create(GiphyApi::class.java) }

    factory { GiphyResultPayloadMapper() }
    factory { TrendingGifsEndpoint(get(), get()) }
}

private val useCaseModule = module {
    factory { LoadTrendingGifsUseCase(get()) }
}

val applicationModules = listOf(httpModule, useCaseModule)