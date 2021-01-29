package br.com.github.dimrsilva.giphy.application.http.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GiphyResultPayload(
    @Json(name = "data") val data: List<GiphyPayload>,
    @Json(name = "pagination") val pagination: PaginationPayload,
) {
    @JsonClass(generateAdapter = true)
    data class GiphyPayload(
        @Json(name = "id") val id: String,
        @Json(name = "url") val url: String,
        @Json(name = "images") val images: ImagesPayload,
    )

    @JsonClass(generateAdapter = true)
    data class ImagesPayload(
        @Json(name = "fixed_width") val fixedWidth: Mp4Payload,
    )

    @JsonClass(generateAdapter = true)
    data class Mp4Payload(
        @Json(name = "mp4") val mp4: String,
        @Json(name = "width") val width: Int,
        @Json(name = "height") val height: Int,
    )

    @JsonClass(generateAdapter = true)
    data class PaginationPayload(
        @Json(name = "offset") val offset: Int,
        @Json(name = "total_count") val totalCount: Int,
    )
}
