package br.com.github.dimrsilva.giphy.presentation

import br.com.github.dimrsilva.giphy.presentation.favorites.FavoritesViewModel
import br.com.github.dimrsilva.giphy.presentation.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val favoritesModule = module {
    viewModel { FavoritesViewModel(get(), get()) }
}

private val searchModule = module {
    viewModel { SearchViewModel(get(), get()) }
}

val presentationModules = listOf(favoritesModule, searchModule)