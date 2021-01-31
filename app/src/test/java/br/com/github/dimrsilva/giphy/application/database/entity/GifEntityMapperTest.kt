package br.com.github.dimrsilva.giphy.application.database.entity

import br.com.github.dimrsilva.giphy.application.database.entity.GifEntityMapper
import br.com.github.dimrsilva.giphy.application.model.Gif
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GifEntityMapperTest {

    private lateinit var mapper: GifEntityMapper

    @Before
    fun before() {
        mapper = GifEntityMapper()
    }

    @Test
    fun whenReceiveModelShouldMapToEntity() {
        val model = Gif(
            id = "id",
            url = "url",
            mp4Url = "mp4Url",
            width = 200,
            height = 100,
            isFavorite = true,
        )

        val entity = mapper.map(model)

        assertEquals("id", entity.id)
        assertEquals("url", entity.url)
        assertEquals("mp4Url", entity.mp4Url)
        assertEquals(200, entity.width)
        assertEquals(100, entity.height)
    }
}