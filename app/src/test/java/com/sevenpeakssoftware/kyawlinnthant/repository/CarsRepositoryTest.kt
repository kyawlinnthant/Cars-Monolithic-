package com.sevenpeakssoftware.kyawlinnthant.repository

import com.google.common.truth.Truth.assertThat
import com.sevenpeakssoftware.kyawlinnthant.core.Result
import com.sevenpeakssoftware.kyawlinnthant.data.db.CarEntity
import com.sevenpeakssoftware.kyawlinnthant.data.db.CarsDao
import com.sevenpeakssoftware.kyawlinnthant.data.ds.PrefDataStore
import com.sevenpeakssoftware.kyawlinnthant.data.remote.ApiService
import com.sevenpeakssoftware.kyawlinnthant.data.remote.CarsDto
import com.sevenpeakssoftware.kyawlinnthant.domain.model.asThemeType
import com.sevenpeakssoftware.kyawlinnthant.domain.model.asVo
import com.sevenpeakssoftware.kyawlinnthant.domain.repo.CarsRepositoryImpl
import com.sevenpeakssoftware.test_rule.TestCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CarsRepositoryTest {

    private lateinit var service: ApiService
    private lateinit var dao: CarsDao
    private lateinit var pref: PrefDataStore
    private var repository: CarsRepositoryImpl? = null

    @get:Rule
    val testRule = TestCoroutinesRule()

    @Before
    fun setup() {
        service = mock(ApiService::class.java)
        dao = mock(CarsDao::class.java)
        pref = mock(PrefDataStore::class.java)
        repository = CarsRepositoryImpl(
            carsDao = dao,
            apiService = service,
            pref = pref,
            dispatcher = testRule.testDispatcher,
        )
    }

    @After
    fun teardown() {
        repository = null
    }

    @Test
    fun `fetchCars successfully transform data`() = runTest {
        val mockResponse = CarsDto(
            status = "",
            serverTime = 1L,
            content = emptyList(),
        )
        //assume with mock
        `when`(service.fetchCars()).thenReturn(Response.success(mockResponse))
        val actual = repository!!.fetchCars()
        assertThat(actual as Result.Success).isEqualTo(Result.Success<List<CarsDto>>(emptyList()))
    }

    @Test
    fun `fetchCars got error and transform result error`() = runTest {
        `when`(service.fetchCars()).thenReturn(
            Response.error(
                400,
                "error response".toResponseBody("txt".toMediaTypeOrNull())
            )
        )
        val actual = repository!!.fetchCars()
        assertThat(actual).isEqualTo(Result.Error("Response.error()"))
    }

    @Test
    fun `readCars from db successfully transform vo`() = runTest {
        val mockResponse = listOf(
            CarEntity(
                id = 1,
                image = "",
                title = "",
                publishedDate = "",
                ingress = ""
            )
        )
        //assume with mock
        `when`(dao.readCars()).thenReturn(flowOf(mockResponse))
        val actual = repository!!.readCars().first()
        assertThat(actual).isEqualTo(mockResponse.map { it.asVo() })
    }

    @Test
    fun `read specific car by id returns correct vo`() = runTest {
        val mockResponse = CarEntity(
            id = 1,
            image = "",
            title = "",
            publishedDate = "",
            ingress = ""
        )
        //assume with mock
        `when`(dao.readSpecificCar(1)).thenReturn(flowOf(mockResponse))
        val actual = repository!!.readSpecificCar(1).first()
        assertThat(actual).isEqualTo(mockResponse.asVo())
    }

    @Test
    fun `correctly returns dynamic color`() = runTest {
        `when`(pref.pullDynamicColor()).thenReturn(flowOf(true))
        val actual = repository!!.pullDynamicColor().first()
        assertThat(actual).isTrue()
    }

    @Test
    fun `correctly returns theme color`() = runTest {
        `when`(pref.pullThemeColor()).thenReturn(flowOf(1))
        val actual = repository!!.pullThemeColor().first()
        assertThat(actual).isEqualTo(1.asThemeType())
    }
}