package com.example.bcpexchange.core.functional

sealed class Either<out L, out R> {

    data class Error<out L>(val value: L) : Either<L, Nothing>()
    data class Success<out R>(val value: R) : Either<Nothing, R>()

    fun <L1> mapFailure(fn: (L) -> L1): Either<L1, R> =
        when (this) {
            is Error -> Error(fn(value))
            is Success -> Success(value)
        }

    fun <R1> mapSuccess(fn: (R) -> R1): Either<L, R1> =
        when (this) {
            is Error -> Error(value)
            is Success -> Success(fn(value))
        }
}

