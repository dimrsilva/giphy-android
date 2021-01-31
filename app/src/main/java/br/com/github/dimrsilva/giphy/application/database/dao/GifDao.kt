package br.com.github.dimrsilva.giphy.application.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.github.dimrsilva.giphy.application.database.entity.FavoriteEntryEntity
import br.com.github.dimrsilva.giphy.application.database.entity.FavoriteGifTuple
import br.com.github.dimrsilva.giphy.application.database.entity.GifEntity
import br.com.github.dimrsilva.giphy.application.database.entity.SearchEntryEntity

@Dao
interface GifDao {
    @Query("""
       SELECT *, favorite_entries.gif_id IS NOT NULL as is_favorite
        FROM favorite_entries
        INNER JOIN gifs ON favorite_entries.gif_id == gifs.id
        ORDER BY `index` DESC
    """)
    fun getFavoriteEntries(): DataSource.Factory<Int, FavoriteGifTuple>

    @Query("""
       SELECT *, favorite_entries.gif_id IS NOT NULL as is_favorite
        FROM search_entries
        INNER JOIN gifs ON search_entries.gif_id == gifs.id
        LEFT JOIN favorite_entries ON search_entries.gif_id == favorite_entries.gif_id
        ORDER BY `index` ASC
    """)
    fun getSearchEntries(): DataSource.Factory<Int, FavoriteGifTuple>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGifs(entities: List<GifEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchEntries(entities: List<SearchEntryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteEntry(entity: FavoriteEntryEntity)

    @Query("DELETE FROM favorite_entries WHERE gif_id = :gifId")
    suspend fun removeFavoriteEntry(gifId: String)

    @Query("""
        DELETE FROM gifs WHERE id NOT IN (
            SELECT gif_id FROM favorite_entries
        )
    """)
    suspend fun clearOldGifs()

    @Query("DELETE FROM search_entries")
    suspend fun clearSearchEntries()

    @Query("SELECT `index` FROM search_entries ORDER BY `index` DESC LIMIT 1")
    suspend fun lastSearchIndex(): Int?
}