package com.sevenpeakssoftware.kyawlinnthant.core

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowSettings

@RunWith(RobolectricTestRunner::class)
class DateTimeConverterTest {

    @Test
    fun `input Date in current year and system is 12h format`() {
        ShadowSettings.set24HourTimeFormat(false)
        val input = "05.02.2023 04:20"
        val actual = DateTimeConverter.transform(is24 = false, input = input)
        val expected = "05 Feb,04:20 AM"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `input Date is not in current year and system is 12h format`() {
        ShadowSettings.set24HourTimeFormat(false)
        val input = "05.02.2022 04:20"
        val actual = DateTimeConverter.transform(is24 = false, input = input)
        val expected = "05 Feb 2022,04:20 AM"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `input Date in current year and system is 24h format`() {
        ShadowSettings.set24HourTimeFormat(true)
        val input = "05.02.2023 13:20"
        val actual = DateTimeConverter.transform(is24 = true, input = input)
        val expected = "05 Feb,13:20"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `input Date is not in current year and system is 24h format`() {
        ShadowSettings.set24HourTimeFormat(true)
        val input = "05.02.2022 13:20"
        val actual = DateTimeConverter.transform(is24 = true, input = input)
        val expected = "05 Feb 2022,13:20"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `input Date is specious then return will be empty`() {
        val malformed = "non sense data"
        val actual1 = DateTimeConverter.transform(is24 = false, input = malformed)
        val wrongType = "Jan 2,2023 - 11:11:11"
        val actual2 = DateTimeConverter.transform(is24 = false, input = wrongType)
        val empty = ""
        val actual3 = DateTimeConverter.transform(is24 = false, input = empty)

        assertThat(actual1).isEmpty()
        assertThat(actual2).isEmpty()
        assertThat(actual3).isEmpty()
    }
}