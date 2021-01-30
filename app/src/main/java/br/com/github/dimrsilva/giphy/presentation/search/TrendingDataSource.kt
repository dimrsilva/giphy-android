package br.com.github.dimrsilva.giphy.presentation.search

import androidx.paging.PositionalDataSource
import br.com.github.dimrsilva.giphy.application.SafeResult
import br.com.github.dimrsilva.giphy.application.model.Gif
import br.com.github.dimrsilva.giphy.application.usecase.LoadTrendingGifsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TrendingDataSource(
    private val coroutineScope: CoroutineScope,
    private val loadTrendingGifsUseCase: LoadTrendingGifsUseCase,
) : PositionalDataSource<Gif>() {
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Gif>) {
        coroutineScope.launch {
            when (val result = loadTrendingGifsUseCase.load(30, 0)) {
                is SafeResult.Success -> callback.onResult(result.data.gifs, result.data.offset, result.data.totalCount)
                is SafeResult.Failure -> callback.onResult(emptyList(), 0, 0)
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Gif>) {
        coroutineScope.launch {
            when (val result = loadTrendingGifsUseCase.load(30, params.startPosition)) {
                is SafeResult.Success -> callback.onResult(result.data.gifs)
                is SafeResult.Failure -> callback.onResult(emptyList())
            }
        }
    }
}