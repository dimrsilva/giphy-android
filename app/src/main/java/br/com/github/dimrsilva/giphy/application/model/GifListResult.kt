package br.com.github.dimrsilva.giphy.application.model

data class GifListResult(
    val gifs: List<Gif>,
    val offset: Int,
    val totalCount: Int,
)