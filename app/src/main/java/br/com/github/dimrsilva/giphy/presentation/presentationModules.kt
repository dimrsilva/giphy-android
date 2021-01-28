package br.com.github.dimrsilva.giphy.presentation

import br.com.github.dimrsilva.giphy.presentation.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
}

val presentationModules = listOf(viewModelModule)