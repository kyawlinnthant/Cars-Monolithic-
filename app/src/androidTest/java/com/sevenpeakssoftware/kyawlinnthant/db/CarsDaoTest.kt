package com.sevenpeakssoftware.kyawlinnthant.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.sevenpeakssoftware.kyawlinnthant.data.db.CarEntity
import com.sevenpeakssoftware.kyawlinnthant.data.db.CarsDao
import com.sevenpeakssoftware.kyawlinnthant.data.db.CarsDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CarsDaoTest {
    @get:Rule
    val mainRule = HiltAndroidRule(this)

    private lateinit var dao: CarsDao

    @Inject
    lateinit var db: CarsDatabase

    @Before
    fun setup() {
        mainRule.inject()
        dao = db.getCarsDao()
    }

    @After
    fun teardown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun insert_cars_successfully() = runTest {
        val dummy = CarEntity(
            id = 1,
            image = "dummy.jpg",
            title = "Dummy",
            publishedDate = "12:12",
            ingress = "Dummy on dummy."
        )
        dao.insertCars(cars = listOf(dummy))
        val actual = dao.readCars().first()
        assertThat(actual.size).isEqualTo(1)
        assertThat(actual.first().id).isEqualTo(dummy.id)
        assertThat(actual.first().image).isEqualTo(dummy.image)
        assertThat(actual.first().title).isEqualTo(dummy.title)
        assertThat(actual.first().publishedDate).isEqualTo(dummy.publishedDate)
        assertThat(actual.first().ingress).isEqualTo(dummy.ingress)
    }

    @Test
    fun insert_again_with_same_primary_key_should_be_replaced() = runTest {
        //first time insertion
        val firstCar = CarEntity(
            id = 1,
            image = "dummy.jpg",
            title = "Dummy",
            publishedDate = "12:12",
            ingress = "Dummy on dummy."
        )
        dao.insertCars(cars = listOf(firstCar))

        //second time insertion with same id
        val secondCar = CarEntity(
            id = 1,
            image = "new car.jpg",
            title = "New car title",
            publishedDate = "11:11",
            ingress = "New car ingress."
        )
        dao.insertCars(cars = listOf(secondCar))

        val actual = dao.readCars().first()
        assertThat(actual.size).isEqualTo(1)
        assertThat(actual.first().id).isEqualTo(secondCar.id)
        assertThat(actual.first().image).isEqualTo(secondCar.image)
        assertThat(actual.first().title).isEqualTo(secondCar.title)
        assertThat(actual.first().publishedDate).isEqualTo(secondCar.publishedDate)
        assertThat(actual.first().ingress).isEqualTo(secondCar.ingress)
    }

    @Test
    fun no_data_in_db_should_return_empty() = runTest {
        val result = dao.readCars().first()
        assertThat(result).isEmpty()
    }

    @Test
    fun specific_car_with_id_return_correct_data() = runTest {
        val car1 =
            CarEntity(
                id = 1,
                image = "new car.jpg",
                title = "New car title",
                publishedDate = "11:11",
                ingress = "New car ingress."
            )
        val car2 =
            CarEntity(
                id = 2,
                image = "dummy.jpg",
                title = "Dummy",
                publishedDate = "12:12",
                ingress = "Dummy on dummy."
            )

        dao.insertCars(cars = listOf(car1, car2))
        val result = dao.readSpecificCar(1).first()

        assertThat(result.id).isEqualTo(car1.id)
        assertThat(result.image).isEqualTo(car1.image)
        assertThat(result.title).isEqualTo(car1.title)
        assertThat(result.publishedDate).isEqualTo(car1.publishedDate)
        assertThat(result.ingress).isEqualTo(car1.ingress)
    }
}