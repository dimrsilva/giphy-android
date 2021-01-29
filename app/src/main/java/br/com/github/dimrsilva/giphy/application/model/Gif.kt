package br.com.github.dimrsilva.giphy.application.model

data class Gif(
    val index: Int,
    val id: String,
    val url: String,
    val mp4Url: String,
    val width: Int,
    val height: Int,
    val isFavorite: Boolean,
)