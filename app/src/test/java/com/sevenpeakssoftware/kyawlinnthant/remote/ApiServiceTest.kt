package com.sevenpeakssoftware.kyawlinnthant.remote

import com.google.common.truth.Truth.assertThat
import com.google.gson.stream.MalformedJsonException
import com.sevenpeakssoftware.kyawlinnthant.data.remote.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@OptIn(ExperimentalCoroutinesApi::class)
class ApiServiceTest {
    private lateinit var service: ApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    private fun enqueueResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val mockResponse = MockResponse()
        val source = inputStream.source().buffer()
        mockWebServer.enqueue(
            mockResponse.setBody(source.readString(Charsets.UTF_8))
        )
    }

    @Test
    fun `fetch cars return success with 200`() = runTest {
        enqueueResponse("success-cars-200.json")
        val response = service.fetchCars()
        val request = mockWebServer.takeRequest()
        //is correct request
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/" + ApiService.CAR_LIST)
        //is correct response
        assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK)
        assertThat(response.body()!!.status).isEqualTo("success")
        assertThat(response.body()!!.serverTime).isEqualTo(1668732951L)
        assertThat(response.body()!!.content.size).isEqualTo(9)
    }

    @Test
    fun `fetch cars - 4xx`() = runTest {
        val expectedResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueue(expectedResponse)
        val actual = service.fetchCars()
        assertThat(actual.code()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND)
    }

    @Test
    fun `fetch cars - 5xx`() = runTest {
        val expectedResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(expectedResponse)
        val actual = service.fetchCars()
        assertThat(actual.code()).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

    @Test(expected = MalformedJsonException::class)
    fun `malformed json throws exception`() = runTest {
        enqueueResponse("malformed.json")
        service.fetchCars()
    }
}