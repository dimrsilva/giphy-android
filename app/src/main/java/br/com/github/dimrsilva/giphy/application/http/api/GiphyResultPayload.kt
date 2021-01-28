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
    )

    @JsonClass(generateAdapter = true)
    data class PaginationPayload(
        @Json(name = "offset") val offset: Int,
        @Json(name = "total_count") val totalCount: Int,
    )
}
