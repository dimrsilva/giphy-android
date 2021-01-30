package br.com.github.dimrsilva.giphy.application.usecase

import br.com.github.dimrsilva.giphy.application.SafeResult
import br.com.github.dimrsilva.giphy.application.http.SearchGifsEndpoint
import br.com.github.dimrsilva.giphy.application.model.GifListResult
import br.com.github.dimrsilva.giphy.application.runSafely

class SearchGifsUseCase(
    private val searchGifsEndpoint: SearchGifsEndpoint,
    private val populateFavoriteFieldUseCase: PopulateFavoriteFieldUseCase,
) {
    suspend fun load(searchTerm: String, limit: Int, offset: Int): SafeResult<GifListResult> {
        return runSafely {
            val result = searchGifsEndpoint.call(searchTerm, limit, offset)
            val newGifs = populateFavoriteFieldUseCase.populate(
                result.gifs
            )
            result.copy(gifs = newGifs)
        }
    }
}