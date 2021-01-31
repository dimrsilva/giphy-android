package br.com.github.dimrsilva.giphy.application

import br.com.github.dimrsilva.giphy.application.database.mapper.FavoriteGifTupleMapper
import br.com.github.dimrsilva.giphy.application.database.mapper.GifEntityMapper
import br.com.github.dimrsilva.giphy.infrastructure.database.GifDatabase
import br.com.github.dimrsilva.giphy.application.database.repository.GifRepository
import br.com.github.dimrsilva.giphy.application.http.SearchGifsEndpoint
import br.com.github.dimrsilva.giphy.application.http.TrendingGifsEndpoint
import br.com.github.dimrsilva.giphy.application.http.api.GiphyApi
import br.com.github.dimrsilva.giphy.application.http.api.GiphyResultPayloadMapper
import br.com.github.dimrsilva.giphy.application.usecase.LoadFavoriteGifsUseCase
import br.com.github.dimrsilva.giphy.application.usecase.ToggleFavoriteGifUseCase
import br.com.github.dimrsilva.giphy.application.usecase.SearchGifsUseCase
import org.koin.dsl.module
import retrofit2.Retrofit

private val databaseModule = module {
    factory { get<GifDatabase>().gifDao() }

    factory { GifEntityMapper() }
    factory { FavoriteGifTupleMapper() }

    factory { GifRepository(get(), get(), get()) }
}

private val httpModule = module {
    factory { get<Retrofit>().create(GiphyApi::class.java) }

    factory { GiphyResultPayloadMapper() }
    factory { TrendingGifsEndpoint(get(), get()) }
    factory { SearchGifsEndpoint(get(), get()) }
}

private val useCaseModule = module {
    factory { LoadFavoriteGifsUseCase(get()) }
    factory { SearchGifsUseCase(get(), get(), get()) }
    factory { ToggleFavoriteGifUseCase(get()) }
}

val applicationModules = listOf(databaseModule, httpModule, useCaseModule)