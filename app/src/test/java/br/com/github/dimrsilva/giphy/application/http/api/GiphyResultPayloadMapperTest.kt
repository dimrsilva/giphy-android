package br.com.github.dimrsilva.giphy.application.http.api

import br.com.github.dimrsilva.giphy.application.model.Gif
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GiphyResultPayloadMapperTest {
    private lateinit var mapper: GiphyResultPayloadMapper

    @Before
    fun before() {
        mapper = GiphyResultPayloadMapper()
    }

    @Test
    fun whenEmptyResultShouldReturnEmptyValue() {
        val payload = GiphyResultPayload(
            data = emptyList(),
            pagination = GiphyResultPayload.PaginationPayload(0, 0)
        )

        val result = mapper.map(payload)

        assertEquals(emptyList<Gif>(), result.gifs)
        assertEquals(0, result.offset)
        assertEquals(0, result.totalCount)
    }

    @Test
    fun whenHasResultShouldReturnFilledValues() {
        val payload = GiphyResultPayload(
            data = listOf(dummy),
            pagination = GiphyResultPayload.PaginationPayload(0, 1)
        )

        val result = mapper.map(payload)

        assertEquals(listOf(Gif(
            id = "id",
            url = "url",
            mp4Url = "mp4",
            width = 100,
            height = 200,
            isFavorite = false,
        )), result.gifs)
        assertEquals(0, result.offset)
        assertEquals(1, result.totalCount)
    }

    @Test
    fun whenNullFixedWidthMp4ShouldReturnOriginal() {
        val payload = GiphyResultPayload(
            data = listOf(
                dummy.copy(
                    images = dummy.images.copy(
                        fixedWidth = dummy.images.fixedWidth.copy(
                            mp4 = null
                        )
                    )
                )
            ),
            pagination = GiphyResultPayload.PaginationPayload(10, 11)
        )

        val result = mapper.map(payload)

        assertEquals(listOf(Gif(
            id = "id",
            url = "url",
            mp4Url = "mp4_original",
            width = 200,
            height = 400,
            isFavorite = false,
        )), result.gifs)
        assertEquals(10, result.offset)
        assertEquals(11, result.totalCount)
    }

    companion object {
        private val dummy = GiphyResultPayload.GiphyPayload(
            id = "id",
            url = "url",
            images = GiphyResultPayload.ImagesPayload(
                fixedWidth = GiphyResultPayload.Mp4Payload(
                    mp4 = "mp4",
                    width = 100,
                    height = 200
                ),
                original = GiphyResultPayload.Mp4Payload(
                    mp4 = "mp4_original",
                    width = 200,
                    height = 400
                ),
            )
        )
    }
}