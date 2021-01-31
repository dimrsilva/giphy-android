package br.com.github.dimrsilva.giphy.application.database.repository

import androidx.paging.DataSource
import br.com.github.dimrsilva.giphy.application.database.dao.GifDao
import br.com.github.dimrsilva.giphy.application.database.entity.FavoriteEntryEntity
import br.com.github.dimrsilva.giphy.application.database.entity.SearchEntryEntity
import br.com.github.dimrsilva.giphy.application.database.mapper.FavoriteGifTupleMapper
import br.com.github.dimrsilva.giphy.application.database.mapper.GifEntityMapper
import br.com.github.dimrsilva.giphy.application.model.Gif

class GifRepository(
    private val gifDao: GifDao,
    private val mapper: GifEntityMapper,
    private val tupleMapper: FavoriteGifTupleMapper,
) {
    suspend fun saveFavorite(id: String) {
        gifDao.insertFavoriteEntry(FavoriteEntryEntity(gifId = id))
    }

    suspend fun removeFavorite(id: String) {
        gifDao.removeFavoriteEntry(id)
    }

    suspend fun saveSearchResults(gifs: List<Gif>, startIndex: Int) {
        gifDao.insertGifs(gifs.map(mapper::map))
        gifDao.insertSearchEntries(gifs.mapIndexed { index, gif ->
            SearchEntryEntity(
                index = startIndex + index,
                gifId = gif.id,
            )
        })
    }

    suspend fun clearSearchResults() {
        gifDao.clearSearchEntries()
        gifDao.clearOldGifs()
    }

    fun getFavoriteEntries(): DataSource.Factory<Int, Gif> {
        return gifDao.getFavoriteEntries().map { tupleMapper.map(it) }
    }

    fun getSearchEntries(): DataSource.Factory<Int, Gif> {
        return gifDao.getSearchEntries().map { tupleMapper.map(it) }
    }

    suspend fun lastSearchIndex(): Int? {
        return gifDao.lastSearchIndex()
    }
}