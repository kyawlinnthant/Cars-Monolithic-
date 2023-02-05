package com.sevenpeakssoftware.kyawlinnthant.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevenpeakssoftware.kyawlinnthant.domain.model.CarVo
import com.sevenpeakssoftware.kyawlinnthant.domain.repo.CarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: CarsRepository,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {

    private val vmState = MutableStateFlow(CarVo())
    val uiState get() = vmState.asStateFlow()

    init {
        savedStateHandle.get<Int>(key = "id")?.let {
            readNews(id = it)
        }
    }

    private fun readNews(id: Int) {
        viewModelScope.launch {
            repository.readSpecificCar(id).collect {
                vmState.emit(it)
            }
        }
    }
}