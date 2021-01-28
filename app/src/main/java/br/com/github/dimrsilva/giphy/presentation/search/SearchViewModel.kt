package br.com.github.dimrsilva.giphy.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import br.com.github.dimrsilva.giphy.application.model.Gif
import br.com.github.dimrsilva.giphy.application.usecase.LoadTrendingGifsUseCase

class SearchViewModel(
    loadTrendingGifsUseCase: LoadTrendingGifsUseCase,
) : ViewModel() {
    private val searchDataSourceFactory = SearchDataSourceFactory(viewModelScope, loadTrendingGifsUseCase)

    val pages: LiveData<PagedList<Gif>> = searchDataSourceFactory.toLiveData(30)
}