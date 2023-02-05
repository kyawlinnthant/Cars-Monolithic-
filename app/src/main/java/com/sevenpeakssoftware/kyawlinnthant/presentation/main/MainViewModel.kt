package com.sevenpeakssoftware.kyawlinnthant.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevenpeakssoftware.kyawlinnthant.domain.model.ThemeType
import com.sevenpeakssoftware.kyawlinnthant.domain.repo.CarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CarsRepository
) : ViewModel() {

    private val vmTheme = MutableStateFlow(ThemeType.DEFAULT)
    val uiTheme get() = vmTheme.asStateFlow()
    private val vmDynamic = MutableStateFlow(true)
    val uiDynamic get() = vmDynamic.asStateFlow()

    init {
        getThemePreference()
        getDynamicPreference()
    }

    private fun getThemePreference() {
        viewModelScope.launch {
            repository.pullThemeColor().collect {
                vmTheme.emit(it)
            }
        }
    }

    private fun getDynamicPreference() {
        viewModelScope.launch {
            repository.pullDynamicColor().collect {
                vmDynamic.emit(it)
            }
        }
    }
}