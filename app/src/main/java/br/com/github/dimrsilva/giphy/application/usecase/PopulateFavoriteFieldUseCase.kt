package br.com.github.dimrsilva.giphy.application.usecase

import br.com.github.dimrsilva.giphy.application.database.repository.GifRepository
import br.com.github.dimrsilva.giphy.application.model.Gif

class PopulateFavoriteFieldUseCase(
    private val gifRepository: GifRepository
) {
    suspend fun populate(list: List<Gif>): List<Gif> {
        val ids = list.map { it.id }
        val favorites = gifRepository.loadFavorites(ids)
        return list.map {
            it.copy(isFavorite = favorites.contains(it.id))
        }
    }
}