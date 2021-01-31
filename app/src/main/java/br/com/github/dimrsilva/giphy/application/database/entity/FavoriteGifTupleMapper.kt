package br.com.github.dimrsilva.giphy.application.database.entity

import br.com.github.dimrsilva.giphy.application.model.Gif

class FavoriteGifTupleMapper {
    fun map(tuple: FavoriteGifTuple) = Gif(
        id = tuple.id,
        url = tuple.url,
        mp4Url = tuple.mp4Url,
        width = tuple.width,
        height = tuple.height,
        isFavorite = tuple.isFavorite,
    )
}