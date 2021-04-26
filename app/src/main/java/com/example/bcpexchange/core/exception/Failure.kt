package com.example.bcpexchange.core.exception

sealed class Failure {
    object NetworkFailure : Failure()
    object ServerFailure : Failure()
    object UnexpectedFailure : Failure()
}
