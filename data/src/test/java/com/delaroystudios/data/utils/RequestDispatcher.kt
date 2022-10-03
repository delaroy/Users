package me.tap.data.utils

import com.delaroystudios.data.api.EndPoint.USERS
import com.delaroystudios.data.utils.UsersFakeData
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

internal class RequestDispatcher : Dispatcher() {

    private var shouldFail = false

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/$USERS" ->
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(if (shouldFail)
                        UsersFakeData.ERROR_USERS_RESPONSE else
                            UsersFakeData.SUCCESS_USERS_RESPONSE)
            else -> throw IllegalArgumentException("Unknown Request Path ${request.path}")
        }
    }

    fun shouldReturnError(value: Boolean) {
        shouldFail = value
    }
}
