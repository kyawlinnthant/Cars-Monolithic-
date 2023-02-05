package com.sevenpeakssoftware.kyawlinnthant.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevenpeakssoftware.kyawlinnthant.core.Result
import com.sevenpeakssoftware.kyawlinnthant.domain.model.ThemeType
import com.sevenpeakssoftware.kyawlinnthant.domain.repo.CarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CarsRepository
) : ViewModel() {
    private val vmState = MutableStateFlow(HomeViewModelState())
    val uiState = vmState
        .map(HomeViewModelState::toUiState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.toUiState()
        )

    private val vmEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent get() = vmEvent.asSharedFlow()

    private val dynamicSetting = MutableStateFlow(false)
    val uiDynamic get() = dynamicSetting.asStateFlow()

    private val themeSetting = MutableStateFlow(ThemeType.DEFAULT)
    val uiTheme get() = themeSetting.asStateFlow()

    var shouldShowDialog = mutableStateOf(false)
        private set

    init {
        getThemePreference()
        getDynamicPreference()
        readCars()
        fetchCars()
    }

    private fun getThemePreference() {
        viewModelScope.launch {
            repository.pullThemeColor().collect {
                themeSetting.value = it
            }
        }
    }

    private fun getDynamicPreference() {
        viewModelScope.launch {
            repository.pullDynamicColor().collect {
                dynamicSetting.value = it
            }
        }
    }

    private fun readCars() {
        viewModelScope.launch {
            repository.readCars().collect { list ->
                vmState.update {
                    it.copy(
                        cars = list,
                        isLoading = it.isLoading,
                        error = it.error
                    )
                }
            }
        }

    }

    fun fetchCars() {
        vmState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = repository.fetchCars()) {
                is Result.Error -> {
                    vmState.update {
                        it.copy(
                            error = result.message,
                            isLoading = false,
                            cars = it.cars
                        )
                    }
                    delay(500L)
                    if (vmState.value.cars.isNotEmpty())
                        vmEvent.emit(HomeUiEvent.NetworkError(message = result.message))
                }
                is Result.Success -> {
                    vmState.update {
                        it.copy(
                            cars = it.cars,
                            isLoading = false,
                            error = ""
                        )
                    }
                }
            }
        }
    }

    fun setDialogVisibility(shouldShow: Boolean) {
        shouldShowDialog.value = shouldShow
    }

    fun setTheme(status: ThemeType) {
        viewModelScope.launch {
            repository.putThemeColor(status)
        }
    }

    fun setDynamic(isEnabled: Boolean) {
        viewModelScope.launch {
            repository.putDynamicColor(isEnabled)
        }
    }

    fun navigateToDetail(id: Int) {
        viewModelScope.launch {
            vmEvent.emit(HomeUiEvent.NavigateToDetail(id = id))
        }
    }
}