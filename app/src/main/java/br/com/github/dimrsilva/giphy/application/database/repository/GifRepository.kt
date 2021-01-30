package br.com.github.dimrsilva.giphy.application.database.repository

import androidx.paging.DataSource
import br.com.github.dimrsilva.giphy.application.database.dao.GifDao
import br.com.github.dimrsilva.giphy.application.database.mapper.GifEntityMapper
import br.com.github.dimrsilva.giphy.application.model.Gif

class GifRepository(
    private val gifDao: GifDao,
    private val mapper: GifEntityMapper,
) {
    suspend fun save(gif: Gif) {
        gifDao.insert(mapper.map(gif, System.currentTimeMillis()))
    }

    suspend fun remove(id: String) {
        gifDao.delete(id)
    }

    suspend fun loadFavorites(ids: List<String>): List<String> {
        return gifDao.getFavorites(ids)
    }

    fun load(): DataSource.Factory<Int, Gif> {
        return gifDao.getAll().map { mapper.map(it) }
    }
}