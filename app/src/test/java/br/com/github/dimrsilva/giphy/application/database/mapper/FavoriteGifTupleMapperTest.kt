package br.com.github.dimrsilva.giphy.application.database.mapper

import br.com.github.dimrsilva.giphy.application.database.entity.FavoriteGifTuple
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FavoriteGifTupleMapperTest {

    private lateinit var mapper: FavoriteGifTupleMapper

    @Before
    fun before() {
        mapper = FavoriteGifTupleMapper()
    }

    @Test
    fun whenReceiveTupleShouldMapToModel() {
        val entity = FavoriteGifTuple(
            id = "id",
            url = "url",
            mp4Url = "mp4Url",
            width = 200,
            height = 100,
            isFavorite = true,
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