package br.com.github.dimrsilva.giphy.application.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "search_entries",
    foreignKeys = [
        ForeignKey(
            entity = GifEntity::class,
            parentColumns = ["id"],
            childColumns = ["gif_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = ["gif_id"],
            unique = true,
        )
    ],
)
data class SearchEntryEntity(
    @ColumnInfo(name = "index") val index: Int,
    @PrimaryKey
    @ColumnInfo(name = "gif_id") val gifId: String,
)
