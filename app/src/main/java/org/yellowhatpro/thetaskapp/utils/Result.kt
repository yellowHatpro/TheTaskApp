package org.yellowhatpro.thetaskapp.utils

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response

sealed class Result<T> {
    sealed class Loading<T> : Result<T>() {
        class FromCache<T> : Loading<T>()
        class FromNetwork<T>(val isCacheFetchSuccessful: Boolean = true) : Loading<T>()
    }

    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val exception: Exception) : Result<T>()

    companion object {
        fun <T> loading() = Loading.FromNetwork<T>()
        fun <T> loadingFromCache() = Loading.FromCache<T>()
        fun <T> loadingFromNetwork(isCacheFetchSuccessful: Boolean = true) =
            Loading.FromNetwork<T>(isCacheFetchSuccessful)

        fun <T> success(data: T) = Success(data)
        fun <T> failed(e: Exception) = Failure<T>(e)
        fun <T> failed(errorMessage: String) = Failure<T>(Exception(errorMessage))
    }
}

//TO emit
suspend inline fun <T> Result(
    dispatcher: CoroutineDispatcher? = null,
    crossinline block: suspend () -> T
) : Result<T> {
    return try {
        if (dispatcher != null) {
            withContext(dispatcher) {
                Result.success(block())
            }
        } else Result.success(block())
    } catch (e: Exception) {
        Result.failed(e)
    }
}

// Use this in repository, to collect the Response from Api Service, and emit Flow Results
suspend inline fun <T> Response<T>.collectResult(
    crossinline onSuccess: suspend (T) -> Unit,
    crossinline onFailure: suspend (errorMessage: String) -> Unit
) {
    if (this.isSuccessful) {
        this.body()?.let {
            onSuccess(it)
        }
    } else {
        onFailure(this.message())
    }
}

// Usually in ViewModels and set the values of states
suspend inline fun <T> Flow<Result<T>>.collectResult(
    crossinline onLoading: () -> Unit = {},
    crossinline onSuccess: suspend (T) -> Unit,
    crossinline onFailure: suspend (e: Exception) -> Unit
) {
    this.collect {
        when (it) {
            is Result.Success -> withContext(Dispatchers.Main) { onSuccess(it.data) }
            is Result.Loading -> withContext(Dispatchers.Main) { onLoading() }
            is Result.Failure -> withContext(Dispatchers.Main) { onFailure(it.exception) }
        }
    }
}

@Composable
fun <T> Result<T>.DoOnResult(
     onLoading: @Composable () -> Unit = {},
     onFailure: @Composable (e: Exception) -> Unit,
     onSuccess: @Composable (T) -> Unit,
) {
    when (this) {
        is Result.Failure -> onFailure(this.exception)
        is Result.Loading -> onLoading()
        is Result.Success -> onSuccess(this.data)
    }
}