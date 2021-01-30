package br.com.github.dimrsilva.giphy.application.usecase

import br.com.github.dimrsilva.giphy.application.database.repository.GifRepository
import br.com.github.dimrsilva.giphy.application.model.Gif

class ToggleFavoriteGifUseCase(
    private val gifRepository: GifRepository,
) {
    suspend fun toggle(gif: Gif) {
        if (gif.isFavorite) {
            gifRepository.remove(gif.id)
        } else {
            gifRepository.save(gif)
        }
        gif.isFavorite = !gif.isFavorite
    }
}