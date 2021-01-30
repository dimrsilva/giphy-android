package br.com.github.dimrsilva.giphy.application.database.mapper

import br.com.github.dimrsilva.giphy.application.database.entity.GifEntity
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

        val now = System.currentTimeMillis()
        val entity = mapper.map(model, now)

        assertEquals("id", entity.id)
        assertEquals("url", entity.url)
        assertEquals("mp4Url", entity.mp4Url)
        assertEquals(200, entity.width)
        assertEquals(100, entity.height)
        assertEquals(now, entity.createdAt)
    }

    @Test
    fun whenReceiveEntityShouldMapToModel() {
        val entity = GifEntity(
            id = "id",
            url = "url",
            mp4Url = "mp4Url",
            width = 200,
            height = 100,
            createdAt = System.currentTimeMillis(),
        )

        val model = mapper.map(entity)

        assertEquals("id", model.id)
        assertEquals("url", model.url)
        assertEquals("mp4Url", model.mp4Url)
        assertEquals(200, model.width)
        assertEquals(100, model.height)
        assertTrue(model.isFavorite)
    }
}