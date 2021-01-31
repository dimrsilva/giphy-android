package br.com.github.dimrsilva.giphy.application.usecase

import br.com.github.dimrsilva.giphy.application.database.repository.GifRepository
import br.com.github.dimrsilva.giphy.application.http.SearchGifsEndpoint
import br.com.github.dimrsilva.giphy.application.http.TrendingGifsEndpoint

class SearchGifsUseCase(
    private val gifRepository: GifRepository,
    private val searchGifsEndpoint: SearchGifsEndpoint,
    private val trendingGifsEndpoint: TrendingGifsEndpoint,
) {
    fun load() = gifRepository.getSearchEntries()

    suspend fun clearSearchResults() {
        gifRepository.clearSearchResults()
    }

    suspend fun loadMoreResults(term: String?) {
        val offset = gifRepository.lastSearchIndex()?.let { it + 1 } ?: 0
        val result = if (term.isNullOrEmpty()) {
            trendingGifsEndpoint.call(PAGE_SIZE, offset)
        } else {
            searchGifsEndpoint.call(term, PAGE_SIZE, offset)
        }

        gifRepository.saveSearchResults(result, offset)
    }

    companion object {
        private const val PAGE_SIZE = 30
    }
}