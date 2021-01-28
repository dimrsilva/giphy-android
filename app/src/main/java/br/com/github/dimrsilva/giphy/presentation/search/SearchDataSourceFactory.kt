package br.com.github.dimrsilva.giphy.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.com.github.dimrsilva.giphy.application.model.Gif
import br.com.github.dimrsilva.giphy.application.usecase.LoadTrendingGifsUseCase
import kotlinx.coroutines.CoroutineScope

class SearchDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val loadTrendingGifsUseCase: LoadTrendingGifsUseCase
) : DataSource.Factory<Int, Gif>() {
    val sourceLiveData = MutableLiveData<SearchDataSource>()
    var latestSource: SearchDataSource? = null

    override fun create(): DataSource<Int, Gif> {
        val latestSource = SearchDataSource(coroutineScope, loadTrendingGifsUseCase)
        this.latestSource = latestSource
        sourceLiveData.postValue(latestSource)
        return latestSource
    }
}