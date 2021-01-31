package br.com.github.dimrsilva.giphy.application.usecase

import androidx.paging.DataSource
import br.com.github.dimrsilva.giphy.application.database.repository.GifRepository
import br.com.github.dimrsilva.giphy.application.http.SearchGifsEndpoint
import br.com.github.dimrsilva.giphy.application.http.TrendingGifsEndpoint
import br.com.github.dimrsilva.giphy.application.model.Gif
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchGifsUseCaseTest {
    @MockK
    lateinit var gifRepository: GifRepository

    @MockK
    lateinit var trendingGifsEndpoint: TrendingGifsEndpoint

    @MockK
    lateinit var searchGifsEndpoint: SearchGifsEndpoint

    @InjectMockKs
    lateinit var searchGifsUseCase: SearchGifsUseCase

    @Before
    fun before() {
        MockKAnnotations.init(this)
    }

    @Test
    fun whenLoadShouldReturnFromRepository() {
        val factory = mockk<DataSource.Factory<Int, Gif>>()
        every { gifRepository.getSearchEntries() } returns factory

        val result = searchGifsUseCase.load()

        assertEquals(factory, result)
    }

    @Test
    fun whenClearSearchResultsShouldClearRepository() = runBlocking {
        coEvery { gifRepository.clearSearchResults() } just runs

        searchGifsUseCase.clearSearchResults()

        coVerify { gifRepository.clearSearchResults() }
    }

    @Test
    fun whenLoadMoreResultsWithTermAndWithoutLastSearchIndexShouldSearchAtStart() = runBlocking {
        val mocked = listOf(mockk<Gif>())
        coEvery { gifRepository.lastSearchIndex() } returns null
        coEvery { gifRepository.saveSearchResults(mocked, 0) } just runs
        coEvery { searchGifsEndpoint.call("term", 30, 0) } returns mocked

        searchGifsUseCase.loadMoreResults("term")

        coVerify { gifRepository.saveSearchResults(mocked, 0) }
    }

    @Test
    fun whenLoadMoreResultsWithTermAndWithLastSearchIndexShouldContinueSearch() = runBlocking {
        val mocked = listOf(mockk<Gif>())
        coEvery { gifRepository.lastSearchIndex() } returns 10
        coEvery { gifRepository.saveSearchResults(mocked, 11) } just runs
        coEvery { searchGifsEndpoint.call("term", 30, 11) } returns mocked

        searchGifsUseCase.loadMoreResults("term")

        coVerify { gifRepository.saveSearchResults(mocked, 11) }
    }

    @Test
    fun whenLoadMoreResultsWithoutTermAndWithoutLastSearchIndexShouldLoadTrendingAtStart() = runBlocking {
        val mocked = listOf(mockk<Gif>())
        coEvery { gifRepository.lastSearchIndex() } returns null
        coEvery { gifRepository.saveSearchResults(mocked, 0) } just runs
        coEvery { trendingGifsEndpoint.call(30, 0) } returns mocked

        searchGifsUseCase.loadMoreResults(null)

        coVerify { gifRepository.saveSearchResults(mocked, 0) }
    }

    @Test
    fun whenLoadMoreResultsWithoutTermAndWithLastSearchIndexShouldContinueLoadTrending() = runBlocking {
        val mocked = listOf(mockk<Gif>())
        coEvery { gifRepository.lastSearchIndex() } returns 10
        coEvery { gifRepository.saveSearchResults(mocked, 11) } just runs
        coEvery { trendingGifsEndpoint.call(30, 11) } returns mocked

        searchGifsUseCase.loadMoreResults(null)

        coVerify { gifRepository.saveSearchResults(mocked, 11) }
    }
}