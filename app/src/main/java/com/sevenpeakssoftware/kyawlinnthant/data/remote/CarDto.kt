package com.sevenpeakssoftware.kyawlinnthant.data.remote

import com.sevenpeakssoftware.kyawlinnthant.data.db.CarEntity

data class CarDto(
    val id: Int,
    val changed: Int,
    val content: List<CarContentDto>,
    val created: Int,
    val dateTime: String,
    val image: String,
    val ingress: String,
    val tags: List<Any>,
    val title: String
) {
    fun toEntity() = CarEntity(
        id = this.id,
        image = this.image,
        title = this.title,
        publishedDate = this.dateTime,
        ingress = this.ingress,
    )
}
