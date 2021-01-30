package br.com.github.dimrsilva.giphy.application.http.api

import br.com.github.dimrsilva.giphy.application.model.Gif
import br.com.github.dimrsilva.giphy.application.model.GifListResult

class GiphyResultPayloadMapper {
    fun map(payload: GiphyResultPayload): GifListResult {
        return GifListResult(
            gifs = payload.data.map { it.toGiphy() },
            offset = payload.pagination.offset,
            totalCount = payload.pagination.totalCount
        )
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