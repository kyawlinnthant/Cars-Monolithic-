package com.sevenpeakssoftware.kyawlinnthant.ds

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.sevenpeakssoftware.kyawlinnthant.data.ds.PrefDataStoreImpl
import com.sevenpeakssoftware.kyawlinnthant.data.ds.PrefModule
import com.sevenpeakssoftware.test_rule.TestCoroutinesRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@SmallTest
@UninstallModules(PrefModule::class)
class PrefDataStoreTest {
    @get:Rule
    val coroutinesRule = TestCoroutinesRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private var ds: PrefDataStoreImpl? = null

    @Inject
    lateinit var pref: DataStore<Preferences>

    @Before
    fun setup() {
        hiltRule.inject()
        ds = PrefDataStoreImpl(
            pref = pref,
            dispatcher = coroutinesRule.testDispatcher
        )
    }

    @After
    fun teardown() {
        ds = null
    }

    @Test
    fun initial_dynamic_is_null() = runTest {
        val dynamic = ds!!.pullDynamicColor().first()
        assertThat(dynamic).isNull()
    }

    @Test
    fun initial_theme_is_null() = runTest {
        val theme = ds!!.pullThemeColor().first()
        assertThat(theme).isNull()
    }

    @Test
    fun dynamic_put_and_pull_successfully_work() = runTest {
        ds!!.putDynamicColor(true)
        val expected = ds!!.pullDynamicColor().first()
        assertThat(expected).isTrue()
    }

    @Test
    fun theme_put_and_pull_successfully_work() = runTest {
        ds!!.putThemeColor(100)
        val expected = ds!!.pullThemeColor().first()
        assertThat(expected).isEqualTo(100)
    }
}