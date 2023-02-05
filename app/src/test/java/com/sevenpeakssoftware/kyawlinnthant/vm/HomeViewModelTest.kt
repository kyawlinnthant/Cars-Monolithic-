package com.sevenpeakssoftware.kyawlinnthant.vm

import com.google.common.truth.Truth.assertThat
import com.sevenpeakssoftware.kyawlinnthant.core.Result
import com.sevenpeakssoftware.kyawlinnthant.domain.model.CarVo
import com.sevenpeakssoftware.kyawlinnthant.domain.model.ThemeType
import com.sevenpeakssoftware.kyawlinnthant.domain.repo.CarsRepository
import com.sevenpeakssoftware.kyawlinnthant.presentation.home.HomeUiState
import com.sevenpeakssoftware.kyawlinnthant.presentation.home.HomeViewModel
import com.sevenpeakssoftware.test_rule.TestCoroutinesRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @get:Rule
    val testRule = TestCoroutinesRule()

    @Test
    fun `read Cars from db for first time return empty`() = runTest {
        val vm = HomeViewModel(repository = FakeRepository())
        Dispatchers.setMain(StandardTestDispatcher())
        val vmState = vm.uiState.value
        advanceUntilIdle()
        assertThat(vmState).isEqualTo(HomeUiState.HasCars(emptyList()))
    }

    @Test
    fun `read Cars from network for first time error`() = runTest {
        val repo = FakeRepository()
        val vm = HomeViewModel(repository = repo)
        Dispatchers.setMain(StandardTestDispatcher())
        repo.setNetworkResponse(error = "Something")
        advanceUntilIdle()
        val vmState = vm.uiState.value
        assertThat(vmState).isEqualTo(HomeUiState.FirstTimeError(message = "Something"))
    }

    @Test
    fun `db has data and show data`() = runTest {
        val repo = FakeRepository()
        val vm = HomeViewModel(repository = repo)
        Dispatchers.setMain(StandardTestDispatcher())
        val data = listOf(
            CarVo(),
            CarVo()
        )
        repo.setDbData(data = data)
        advanceUntilIdle()
        val vmState = vm.uiState.value
        assertThat(vmState).isEqualTo(HomeUiState.HasCars(data))
    }

    @Test
    fun `theme state`() = runTest {
        val repo = FakeRepository()
        val vm = HomeViewModel(repository = repo)
        Dispatchers.setMain(StandardTestDispatcher())
        repo.putThemeColor(status = ThemeType.LIGHT)
        advanceUntilIdle()
        val theme = vm.uiTheme.value
        assertThat(theme).isEqualTo(ThemeType.LIGHT)
    }

    @Test
    fun `dynamic state`() = runTest {
        val repo = FakeRepository()
        val vm = HomeViewModel(repository = repo)
        Dispatchers.setMain(StandardTestDispatcher())
        repo.putDynamicColor(true)
        advanceUntilIdle()
        val dynamic = vm.uiDynamic.value
        assertThat(dynamic).isEqualTo(true)
    }


    internal class FakeRepository : CarsRepository {
        private var cars = mutableListOf<CarVo>()
        private var specificCar = CarVo()
        private var isEnabledDynamic = false
        private var themeStatus: ThemeType = ThemeType.DEFAULT
        private var error = ""


        fun setNetworkResponse(error: String) {
            this.error = error
        }

        fun setDbData(data: List<CarVo>) {
            cars.addAll(data)
        }

        override suspend fun fetchCars(): Result<List<CarVo>> {
            return if (error.isEmpty()) Result.Success(data = cars) else Result.Error(error)
        }


        override suspend fun readCars(): Flow<List<CarVo>> {
            return flow { emit(cars) }
        }

        override suspend fun readSpecificCar(id: Int): Flow<CarVo> {
            return flow { emit(specificCar) }
        }

        override suspend fun putDynamicColor(isEnabled: Boolean) {
            isEnabledDynamic = isEnabled
        }

        override suspend fun pullDynamicColor(): Flow<Boolean> {
            return flow { emit(isEnabledDynamic) }
        }

        override suspend fun putThemeColor(status: ThemeType) {
            themeStatus = status
        }

        override suspend fun pullThemeColor(): Flow<ThemeType> {
            return flow { emit(themeStatus) }
        }

    }
}