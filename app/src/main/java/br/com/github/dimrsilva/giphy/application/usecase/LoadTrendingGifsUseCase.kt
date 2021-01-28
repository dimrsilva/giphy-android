package br.com.github.dimrsilva.giphy.application.usecase

import br.com.github.dimrsilva.giphy.application.SafeResult
import br.com.github.dimrsilva.giphy.application.http.TrendingGifsEndpoint
import br.com.github.dimrsilva.giphy.application.model.GifListResult
import br.com.github.dimrsilva.giphy.application.runSafely

class LoadTrendingGifsUseCase(
    private val trendingGifsEndpoint: TrendingGifsEndpoint
) {
    suspend fun load(limit: Int, offset: Int): SafeResult<GifListResult> {
        return runSafely { trendingGifsEndpoint.call(limit, offset) }
    }
}