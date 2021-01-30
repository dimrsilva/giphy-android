package br.com.github.dimrsilva.giphy.application.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gifs")
data class GifEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "mp4_url") val mp4Url: String,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "created_at") val createdAt: Long,
)
