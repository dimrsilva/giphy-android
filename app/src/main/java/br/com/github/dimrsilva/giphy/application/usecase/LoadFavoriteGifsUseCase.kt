package br.com.github.dimrsilva.giphy.application.usecase

import br.com.github.dimrsilva.giphy.application.database.repository.GifRepository

class LoadFavoriteGifsUseCase(
    private val gifRepository: GifRepository,
) {
    fun load() = gifRepository.getFavoriteEntries()
}