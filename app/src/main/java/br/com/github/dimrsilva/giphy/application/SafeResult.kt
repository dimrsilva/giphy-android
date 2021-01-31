package br.com.github.dimrsilva.giphy.application

sealed class SafeResult<out T> {
    data class Success<T>(val data: T) : SafeResult<T>()
    data class Failure(val exception: Exception) : SafeResult<Nothing>()
}

suspend inline fun <T> runSafely(crossinline block: suspend () -> T): SafeResult<T> {
    return try {
        SafeResult.Success(block())
    } catch (e: Exception) {
        SafeResult.Failure(e)
    }
}

fun <F, T> SafeResult<F>.mapSuccess(block : (F) -> T): SafeResult<T> {
    return when(this) {
        is SafeResult.Success -> SafeResult.Success(block(data))
        is SafeResult.Failure -> this
    }
}