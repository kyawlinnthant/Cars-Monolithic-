package com.sevenpeakssoftware.kyawlinnthant.domain.repo

import com.sevenpeakssoftware.kyawlinnthant.core.Result
import com.sevenpeakssoftware.kyawlinnthant.domain.model.CarVo
import com.sevenpeakssoftware.kyawlinnthant.domain.model.ThemeType
import kotlinx.coroutines.flow.Flow

interface CarsRepository {
    suspend fun fetchCars(): Result<List<CarVo>>
    suspend fun readCars(): Flow<List<CarVo>>
    suspend fun readSpecificCar(id : Int) : Flow<CarVo>
    suspend fun putDynamicColor(isEnabled: Boolean)
    suspend fun pullDynamicColor(): Flow<Boolean>
    suspend fun putThemeColor(status: ThemeType)
    suspend fun pullThemeColor(): Flow<ThemeType>
}