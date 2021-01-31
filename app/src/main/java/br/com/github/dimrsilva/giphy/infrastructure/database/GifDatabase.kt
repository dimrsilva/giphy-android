package br.com.github.dimrsilva.giphy.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.github.dimrsilva.giphy.application.database.dao.GifDao
import br.com.github.dimrsilva.giphy.application.database.entity.FavoriteEntryEntity
import br.com.github.dimrsilva.giphy.application.database.entity.GifEntity
import br.com.github.dimrsilva.giphy.application.database.entity.SearchEntryEntity

@Database(
    entities = [
        GifEntity::class,
        SearchEntryEntity::class,
        FavoriteEntryEntity::class,
    ], version = 1
)
abstract class GifDatabase : RoomDatabase() {
    abstract fun gifDao(): GifDao
}