package br.com.github.dimrsilva.giphy.presentation

import br.com.github.dimrsilva.giphy.presentation.search.SearchViewModel
import com.danikula.videocache.HttpProxyCacheServer
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val presentationModule = module {
    single { HttpProxyCacheServer(get()) }
}

private val searchModule = module {
    viewModel { SearchViewModel(get()) }
}

val presentationModules = listOf(presentationModule, searchModule)