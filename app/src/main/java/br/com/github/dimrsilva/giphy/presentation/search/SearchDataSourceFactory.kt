package br.com.github.dimrsilva.giphy.presentation.search

import androidx.paging.DataSource
import br.com.github.dimrsilva.giphy.application.model.Gif
import br.com.github.dimrsilva.giphy.application.usecase.LoadTrendingGifsUseCase
import kotlinx.coroutines.CoroutineScope

class SearchDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val loadTrendingGifsUseCase: LoadTrendingGifsUseCase
) : DataSource.Factory<Int, Gif>() {
    override fun create(): DataSource<Int, Gif> {
        return SearchDataSource(coroutineScope, loadTrendingGifsUseCase)
    }
}