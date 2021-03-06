package br.com.github.dimrsilva.giphy.application.usecase

import br.com.github.dimrsilva.giphy.application.database.repository.GifRepository
import br.com.github.dimrsilva.giphy.application.model.Gif
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ToggleFavoriteGifUseCaseTest {
    @MockK
    lateinit var gifRepository: GifRepository
    lateinit var toggleFavoriteGifUseCase: ToggleFavoriteGifUseCase

    @Before
    fun before() {
        MockKAnnotations.init(this)
        toggleFavoriteGifUseCase = ToggleFavoriteGifUseCase(gifRepository)
    }

    @Test
    fun whenToggleMarkedShouldRemoveFromRepository() = runBlocking {
        coEvery { gifRepository.removeFavorite("id") } just runs
        val gif = dummy.copy(isFavorite = true)

        toggleFavoriteGifUseCase.toggle(gif)

        coVerify { gifRepository.removeFavorite("id") }
    }

    @Test
    fun whenToggleUnmarkedShouldSaveInRepository() = runBlocking {
        val gif = dummy.copy(isFavorite = false)
        coEvery { gifRepository.saveFavorite("id") } just runs

        toggleFavoriteGifUseCase.toggle(gif)

        coVerify { gifRepository.saveFavorite("id") }
    }

    companion object {
        private val dummy = Gif(
            id = "id",
            url = "url",
            mp4Url = "mp4Url",
            width = 200,
            height = 100,
            isFavorite = false,
        )
    }
}