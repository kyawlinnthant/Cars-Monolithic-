package com.sevenpeakssoftware.kyawlinnthant.data.remote

data class CarsDto(
    val content: List<CarDto>,
    val serverTime: Long,
    val status: String
)
