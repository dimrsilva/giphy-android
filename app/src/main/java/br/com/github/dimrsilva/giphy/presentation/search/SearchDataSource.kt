package br.com.github.dimrsilva.giphy.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import br.com.github.dimrsilva.giphy.application.SafeResult
import br.com.github.dimrsilva.giphy.application.model.Gif
import br.com.github.dimrsilva.giphy.application.usecase.LoadTrendingGifsUseCase
import br.com.github.dimrsilva.giphy.application.usecase.SearchGifsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SearchDataSource(
    private val coroutineScope: CoroutineScope,
    private val searchGifsUseCase: SearchGifsUseCase,
    private val searchTerm: String,
    private val loadingLiveData: MutableLiveData<Boolean>,
) : PositionalDataSource<Gif>() {
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Gif>) {
        coroutineScope.launch {
            loadingLiveData.postValue(true)
            when (val result = searchGifsUseCase.load(searchTerm, 30, 0)) {
                is SafeResult.Success -> callback.onResult(result.data.gifs, result.data.offset, result.data.totalCount)
                is SafeResult.Failure -> callback.onResult(emptyList(), 0, 0)
            }
            loadingLiveData.postValue(false)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Gif>) {
        coroutineScope.launch {
            when (val result = searchGifsUseCase.load(searchTerm, 30, params.startPosition)) {
                is SafeResult.Success -> callback.onResult(result.data.gifs)
                is SafeResult.Failure -> callback.onResult(emptyList())
            }
        }
    }
}