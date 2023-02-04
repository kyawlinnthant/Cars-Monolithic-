package com.sevenpeakssoftware.kyawlinnthant.domain.repo

import com.sevenpeakssoftware.kyawlinnthant.core.DispatchersModule
import com.sevenpeakssoftware.kyawlinnthant.core.Result
import com.sevenpeakssoftware.kyawlinnthant.core.safeApiCall
import com.sevenpeakssoftware.kyawlinnthant.data.db.CarsDao
import com.sevenpeakssoftware.kyawlinnthant.data.ds.PrefDataStore
import com.sevenpeakssoftware.kyawlinnthant.data.remote.ApiService
import com.sevenpeakssoftware.kyawlinnthant.domain.model.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CarsRepositoryImpl @Inject constructor(
    private val carsDao: CarsDao,
    private val apiService: ApiService,
    private val pref : PrefDataStore,
    @DispatchersModule.IoDispatcher private val dispatcher : CoroutineDispatcher
) : CarsRepository{

    override suspend fun fetchCars(): Result<List<CarVo>> {
        return when(val result = safeApiCall { apiService.fetchCars() }){
            is Result.Error -> Result.Error(message = result.message)
            is Result.Success -> {
                result.data?.let { dto ->
                    val cars = dto.content.map { it.toEntity() }
                    carsDao.insertCars(cars)
                }
                val carsVo = result.data?.content?.map { it.asVo() }
                Result.Success(data = carsVo)
            }
        }
    }

    override suspend fun readCars(): Flow<List<CarVo>> {
        return carsDao.readCars().map { cars ->
            cars.map { it.asVo() }
        }.flowOn(dispatcher)
    }

    override suspend fun readSpecificCar(id: Int): Flow<CarVo> {
        return carsDao.readSpecificCar(id).map { it.asVo() }.flowOn(dispatcher)
    }

    override suspend fun putDynamicColor(isEnabled: Boolean) {
        withContext(dispatcher){
            pref.putDynamicColor(isEnabled)
        }
    }

    override suspend fun pullDynamicColor(): Flow<Boolean> {
        return pref.pullDynamicColor().map {
            it ?: true //by default, we assume dynamic color is enabled in Android 12+ devices
        }.flowOn(dispatcher)
    }

    override suspend fun putThemeColor(status: ThemeType) {
        withContext(dispatcher){
            pref.putThemeColor(status.asInt())
        }
    }

    override suspend fun pullThemeColor(): Flow<ThemeType> {
        return pref.pullThemeColor().map {
            it?.asThemeType() ?: ThemeType.DEFAULT //by default, we assume the same as SYSTEM THEME
        }.flowOn(dispatcher)
    }
}