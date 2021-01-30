package br.com.github.dimrsilva.giphy.application.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.github.dimrsilva.giphy.application.database.entity.GifEntity

@Dao
interface GifDao {
    @Query("SELECT * FROM gifs ORDER BY created_at DESC")
    suspend fun getAll(): List<GifEntity>

    @Query("SELECT id FROM gifs WHERE id IN (:ids)")
    suspend fun getFavorites(ids: List<String>): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: GifEntity)

    @Query("DELETE FROM gifs WHERE id = :id")
    suspend fun delete(id: String)
}