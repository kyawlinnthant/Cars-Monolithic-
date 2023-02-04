package com.sevenpeakssoftware.kyawlinnthant.domain.model

import com.sevenpeakssoftware.kyawlinnthant.data.db.CarEntity
import com.sevenpeakssoftware.kyawlinnthant.data.remote.CarDto

data class CarVo(
    val id: Int = 0,
    val image: String = "",
    val title: String = "",
    val publishedDate: String = "",
    val ingress: String = "",
)

fun CarEntity.asVo() = CarVo(
    id = this.id,
    image = this.image,
    title = this.title,
    publishedDate = this.publishedDate,
    ingress = this.ingress
)

fun CarDto.asVo() = CarVo(
    id = this.id,
    image = this.image,
    title = this.title,
    publishedDate = this.dateTime,
    ingress = this.ingress
)