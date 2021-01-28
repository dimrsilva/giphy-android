package br.com.github.dimrsilva.giphy.application

sealed class SafeResult<out T> {
    data class Success<T>(val data: T) : SafeResult<T>()
    data class Failure(val exception: Exception) : SafeResult<Nothing>()
}

suspend inline fun <T> runSafely(block: () -> T): SafeResult<T> {
    return try {
        SafeResult.Success(block())
    } catch (e: Exception) {
        SafeResult.Failure(e)
    }
}