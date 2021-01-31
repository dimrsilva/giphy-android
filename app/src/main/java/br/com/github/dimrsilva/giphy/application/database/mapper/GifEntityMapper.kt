package br.com.github.dimrsilva.giphy.application.database.mapper

import br.com.github.dimrsilva.giphy.application.database.entity.GifEntity
import br.com.github.dimrsilva.giphy.application.model.Gif

class GifEntityMapper {
    fun map(gif: Gif) = GifEntity(
        id = gif.id,
        url = gif.url,
        mp4Url = gif.mp4Url,
        width = gif.width,
        height = gif.height,
    )
}