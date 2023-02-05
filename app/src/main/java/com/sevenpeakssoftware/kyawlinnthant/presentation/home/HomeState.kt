package com.sevenpeakssoftware.kyawlinnthant.presentation.home

import com.sevenpeakssoftware.kyawlinnthant.domain.model.CarVo

sealed interface HomeUiState {
    object FirstTimeLoading : HomeUiState
    data class FirstTimeError(val message: String) : HomeUiState
    data class HasCars(val cars: List<CarVo>) : HomeUiState
}

data class HomeViewModelState(
    val cars: List<CarVo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
) {
    fun toUiState(): HomeUiState {
        return when {
            cars.isEmpty() && isLoading -> HomeUiState.FirstTimeLoading
            cars.isEmpty() && error.isNotEmpty() -> HomeUiState.FirstTimeError(message = error)
            else -> HomeUiState.HasCars(cars = cars)
        }
    }
}

sealed interface HomeUiEvent {
    data class NetworkError(val message: String) : HomeUiEvent
    data class NavigateToDetail(val id: Int) : HomeUiEvent
}