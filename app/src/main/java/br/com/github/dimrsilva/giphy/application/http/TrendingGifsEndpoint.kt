package br.com.github.dimrsilva.giphy.application.http

import br.com.github.dimrsilva.giphy.application.http.api.GiphyApi
import br.com.github.dimrsilva.giphy.application.http.api.GiphyResultPayload
import br.com.github.dimrsilva.giphy.application.model.Gif
import br.com.github.dimrsilva.giphy.application.model.GifListResult
import retrofit2.await

class TrendingGifsEndpoint(
    private val giphyApi: GiphyApi,
) {
    suspend fun call(limit: Int, offset: Int): GifListResult {
        return giphyApi.trending(limit, offset).await().let { payload ->
            GifListResult(
                gifs = payload.data.mapIndexed { index, giphy -> giphy.toGiphy(index + payload.pagination.offset) },
                offset = payload.pagination.offset,
                totalCount = payload.pagination.totalCount
            )
        }
    }

    private fun GiphyResultPayload.GiphyPayload.toGiphy(index: Int) = Gif(index, id, url, false)
}