package br.com.github.dimrsilva.giphy.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import br.com.github.dimrsilva.giphy.application.model.Gif
import br.com.github.dimrsilva.giphy.application.usecase.LoadTrendingGifsUseCase
import br.com.github.dimrsilva.giphy.application.usecase.SearchGifsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchViewModel(
    private val loadTrendingGifsUseCase: LoadTrendingGifsUseCase,
    private val searchGifsUseCase: SearchGifsUseCase,
) : ViewModel() {
    private var currentDataSource: DataSource<Int, Gif>? = null
    private val searchDataSourceFactory = SearchDataSourceFactory()
    private val searchChannel = ConflatedBroadcastChannel<String>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    val pages: LiveData<PagedList<Gif>> = searchDataSourceFactory.toLiveData(30)
    val searchTerm = MutableLiveData<String>()

    init {
        searchTerm.observeForever {
            viewModelScope.launch { searchChannel.send(it) }
        }

        viewModelScope.launch {
            searchChannel
                .asFlow()
                .debounce(500L)
                .collect {
                    currentDataSource?.invalidate()
                }
        }
    }

    inner class SearchDataSourceFactory : DataSource.Factory<Int, Gif>() {
        override fun create(): DataSource<Int, Gif> {
            val searchTerm = searchTerm.value
            return if (searchTerm.isNullOrBlank()) {
                TrendingDataSource(viewModelScope, loadTrendingGifsUseCase, _loading)
            } else {
                SearchDataSource(viewModelScope, searchGifsUseCase, searchTerm, _loading)
            }.also {
                currentDataSource = it
            }
        }
    }
}