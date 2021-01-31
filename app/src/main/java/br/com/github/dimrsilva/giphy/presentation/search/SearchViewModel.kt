package br.com.github.dimrsilva.giphy.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import br.com.github.dimrsilva.giphy.application.model.Gif
import br.com.github.dimrsilva.giphy.application.usecase.SearchGifsUseCase
import br.com.github.dimrsilva.giphy.application.usecase.ToggleFavoriteGifUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchViewModel(
    private val searchGifsUseCase: SearchGifsUseCase,
    private val toggleFavoriteGifUseCase: ToggleFavoriteGifUseCase,
) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    val pages: LiveData<PagedList<Gif>> = searchGifsUseCase.load().toLiveData(
        30,
        boundaryCallback = SearchBoundaryCallback()
    )
    val searchTerm = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            searchGifsUseCase.clearSearchResults()
            searchTerm
                .asFlow()
                .debounce(500L)
                .collect {
                    searchGifsUseCase.clearSearchResults()
                }
        }
    }

    fun toggleFavorite(gif: Gif) = viewModelScope.launch {
        toggleFavoriteGifUseCase.toggle(gif)
    }

    inner class SearchBoundaryCallback : PagedList.BoundaryCallback<Gif>() {
        override fun onZeroItemsLoaded() {
            viewModelScope.launch {
                _loading.value = true
                searchGifsUseCase.loadMoreResults(searchTerm.value)
                _loading.value = false
            }
        }

        override fun onItemAtEndLoaded(itemAtEnd: Gif) {
            viewModelScope.launch {
                searchGifsUseCase.loadMoreResults(searchTerm.value)
            }
        }
    }
}