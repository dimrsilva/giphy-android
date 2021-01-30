package br.com.github.dimrsilva.giphy.application.database.mapper

import br.com.github.dimrsilva.giphy.application.database.entity.GifEntity
import br.com.github.dimrsilva.giphy.application.model.Gif

class GifEntityMapper {
    fun map(entity: GifEntity) = Gif(
        id = entity.id,
        url = entity.url,
        mp4Url = entity.mp4Url,
        width = entity.width,
        height = entity.height,
        isFavorite = true,
    )

    fun map(gif: Gif, createdAt: Long) = GifEntity(
        id = gif.id,
        url = gif.url,
        mp4Url = gif.mp4Url,
        width = gif.width,
        height = gif.height,
        createdAt = createdAt
    )
}