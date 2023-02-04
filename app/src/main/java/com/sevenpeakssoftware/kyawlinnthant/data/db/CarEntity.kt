package com.sevenpeakssoftware.kyawlinnthant.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sevenpeakssoftware.kyawlinnthant.data.db.CarEntity.Companion.CARS_TABLE

@Entity(tableName = CARS_TABLE)
data class CarEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val title: String,
    val publishedDate: String,
    val ingress: String
) {
    companion object {
        const val CARS_TABLE = "cars"
    }
}
