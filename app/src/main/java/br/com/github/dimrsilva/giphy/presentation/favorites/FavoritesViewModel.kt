package br.com.github.dimrsilva.giphy.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import br.com.github.dimrsilva.giphy.application.model.Gif
import br.com.github.dimrsilva.giphy.application.usecase.LoadFavoriteGifsUseCase
import br.com.github.dimrsilva.giphy.application.usecase.ToggleFavoriteGifUseCase

class FavoritesViewModel(
    loadFavoriteGifsUseCase: LoadFavoriteGifsUseCase,
    private val toggleFavoriteGifUseCase: ToggleFavoriteGifUseCase,
) : ViewModel() {
    private val _pages = loadFavoriteGifsUseCase.load()
    val pages = _pages.toLiveData(30)

    suspend fun removeFavorite(gif: Gif) {
        toggleFavoriteGifUseCase.toggle(gif)
    }
}