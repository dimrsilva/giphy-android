package br.com.github.dimrsilva.giphy.application.http.api

import br.com.github.dimrsilva.giphy.application.model.Gif

class GiphyResultPayloadMapper {
    fun map(payload: GiphyResultPayload): List<Gif> {
        return payload.data.map { it.toGiphy() }
    }

    private fun GiphyResultPayload.GiphyPayload.toGiphy() =
        if (images.fixedWidth.mp4 != null) {
            Gif(
                id = id,
                url = url,
                mp4Url = images.fixedWidth.mp4,
                width = images.fixedWidth.width,
                height = images.fixedWidth.height,
                isFavorite = false,
            )
        } else {
            Gif(
                id = id,
                url = url,
                mp4Url = images.original.mp4!!,
                width = images.original.width,
                height = images.original.height,
                isFavorite = false,
            )
        }
}