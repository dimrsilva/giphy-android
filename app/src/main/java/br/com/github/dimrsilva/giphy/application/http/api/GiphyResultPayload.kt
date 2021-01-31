package br.com.github.dimrsilva.giphy.application.http.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GiphyResultPayload(
    @Json(name = "data") val data: List<GiphyPayload>,
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
        @Json(name = "original") val original: Mp4Payload,
    )

    @JsonClass(generateAdapter = true)
    data class Mp4Payload(
        @Json(name = "mp4") val mp4: String?,
        @Json(name = "width") val width: Int,
        @Json(name = "height") val height: Int,
    )
}
