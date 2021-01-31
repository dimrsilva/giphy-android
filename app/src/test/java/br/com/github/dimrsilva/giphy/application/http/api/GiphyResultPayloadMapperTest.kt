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
        )

        val result = mapper.map(payload)

        assertEquals(emptyList<Gif>(), result)
    }

    @Test
    fun whenHasResultShouldReturnFilledValues() {
        val payload = GiphyResultPayload(
            data = listOf(dummy),
        )

        val result = mapper.map(payload)

        assertEquals(listOf(Gif(
            id = "id",
            url = "url",
            mp4Url = "mp4",
            width = 100,
            height = 200,
            isFavorite = false,
        )), result)
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
        )

        val result = mapper.map(payload)

        assertEquals(listOf(Gif(
            id = "id",
            url = "url",
            mp4Url = "mp4_original",
            width = 200,
            height = 400,
            isFavorite = false,
        )), result)
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