package com.sevenpeakssoftware.kyawlinnthant.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CarEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CarsDatabase : RoomDatabase() {

    abstract fun getCarsDao(): CarsDao

    companion object {
        const val CARS_DB = "cars.db"
    }
}