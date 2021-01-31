package br.com.github.dimrsilva.giphy.application.http

import br.com.github.dimrsilva.giphy.application.http.api.GiphyApi
import br.com.github.dimrsilva.giphy.application.http.api.GiphyResultPayloadMapper
import br.com.github.dimrsilva.giphy.application.model.Gif
import retrofit2.await

class SearchGifsEndpoint(
    private val mapper: GiphyResultPayloadMapper,
    private val giphyApi: GiphyApi,
) {
    suspend fun call(searchTerm: String, limit: Int, offset: Int): List<Gif> {
        return giphyApi.search(searchTerm, limit, offset).await().let { payload ->
            mapper.map(payload)
        }
    }
}