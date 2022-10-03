package com.delaroystudios.data.common

import com.delaroystudios.domain.common.Resource

/**
 * Handle and log exceptions
 */
suspend fun <T> sendRequest(action: suspend () -> T): Resource<T> =
    try {
        Resource.success(action())
    } catch (e: Exception) {
        Resource.error("Connection error. Try again")
    }




