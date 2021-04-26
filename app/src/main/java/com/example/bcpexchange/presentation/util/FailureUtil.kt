package com.example.bcpexchange.presentation.util

import android.content.Context
import com.example.bcpexchange.R
import com.example.bcpexchange.core.exception.Failure

fun Failure.toMessage(context: Context) = when (this) {
    Failure.NetworkFailure -> context.getString(R.string.network_error_message)
    Failure.ServerFailure -> context.getString(R.string.server_error_message)
    Failure.UnexpectedFailure -> context.getString(R.string.unexpected_error_message)
}