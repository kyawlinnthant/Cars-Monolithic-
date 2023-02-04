package com.sevenpeakssoftware.kyawlinnthant.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCars(cars: List<CarEntity>)

    @Query("DELETE FROM ${CarEntity.CARS_TABLE}")
    suspend fun deleteCars()

    @Query("SELECT * FROM ${CarEntity.CARS_TABLE}")
    fun readCars(): Flow<List<CarEntity>>

    @Query("SELECT * FROM ${CarEntity.CARS_TABLE} WHERE id = :id")
    fun readSpecificCar(id: Int): Flow<CarEntity>

}