object Dependencies {
    //project level dependencies
    private const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    private const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val projectDependencies = arrayListOf<String>().apply {
        add(gradle)
        add(kotlin)
    }
    private const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    val projectHilt get() = hilt

    //app level dependencies
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val lifecycleKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleKtx}"
    val appDependencies = arrayListOf<String>().apply {
        add(coreKtx)
        add(lifecycleKtx)
    }

    //compose
    private const val composeActivity = "androidx.activity:activity-compose:${Versions.activityCompose}"
    private const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    private const val composeTooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    private const val composeJunit = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    private const val composeUiTest = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    private const val composeManifestTest =
        "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
    val appCompose = Requirement(
        implementations = arrayListOf<String>().apply {
            add(composeActivity)
            add(composeUi)
            add(composeTooling)
        },
        androidTestImplementations = arrayListOf<String>().apply {
            add(composeJunit)
        },
        debugImplementations = arrayListOf<String>().apply {
            add(composeUiTest)
            add(composeManifestTest)
        },
    )
    private const val material3 = "androidx.compose.material3:material3:${Versions.material3}"
    val appMaterial get() = material3
    private const val splash = "androidx.core:core-splashscreen:${Versions.splash}"
    val appSplash get() = splash
    private const val systemUi =
        "com.google.accompanist:accompanist-systemuicontroller:${Versions.accomplish}"
    val appSystemUi get() = systemUi
    private const val swifeRefresh =
        "com.google.accompanist:accompanist-swiperefresh:${Versions.accomplish}"
    val appSwifeRefresh get() = swifeRefresh
    private const val coil = "io.coil-kt:coil-compose:${Versions.coil}"
    val appCoil get() = coil
    private const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
    val appMultidex get() = multidex


    //navigation
    private const val navigationCompose =
        "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    val appNavigation get() = navigationCompose

    //hilt
    private const val navigationHilt =
        "androidx.hilt:hilt-navigation-compose:${Versions.navigationHilt}"
    private const val hiltV = "com.google.dagger:hilt-android:${Versions.hilt}"
    private const val hiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    private const val hiltTest = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    val appDi = Requirement(
        implementations = arrayListOf<String>().apply {
            add(navigationHilt)
            add(hiltV)
        },
        kapts = arrayListOf<String>().apply {
            add(hiltKapt)
        },
        testImplementations = arrayListOf<String>().apply {
            add(hiltTest)
        },
        androidTestImplementations = arrayListOf<String>().apply {
            add(hiltTest)
        },
        kaptTests = arrayListOf<String>().apply {
            add(hiltKapt)
        },
        kaptAndroidTests = arrayListOf<String>().apply {
            add(hiltKapt)
        },
    )

    //retrofit
    private const val retrofitV = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    private const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    private const val retrofitCoroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutines}"
    private const val localebro = "com.localebro:okhttpprofiler:${Versions.localebro}"
    private const val mockWebServer = "com.squareup.okhttp3:mockwebserver:4.10.0"
    val appNetwork = Requirement(
        implementations = arrayListOf<String>().apply {
            add(retrofitV)
            add(gson)
            add(okhttp)
            add(retrofitCoroutines)
            add(localebro)
        },
        testImplementations = arrayListOf<String>().apply {
            add(mockWebServer)
        }
    )
    private const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val appTimber get() = timber

    private val desugar = "com.android.tools:desugar_jdk_libs:${Versions.desugar}"
    val appDesugar get() = desugar


    //room
    private const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    private const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    private const val roomProcessor = "androidx.room:room-compiler:${Versions.room}"
    private const val roomKapt = "androidx.room:room-compiler:${Versions.room}"


    val appDb = Requirement(
        implementations = arrayListOf<String>().apply {
            add(roomRuntime)
            add(roomKtx)
        },
        kapts = arrayListOf<String>().apply {
            add(roomKapt)
        },
        annotationProcessors = arrayListOf<String>().apply {
            add(roomProcessor)
        }
    )

    //datastore
    private const val datastorePref = "androidx.datastore:datastore-preferences:${Versions.pref}"
    val appPref get() = datastorePref

    //unit test
    private const val junit = "junit:junit:${Versions.junit}"
    private const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    val appUnitTest = arrayListOf<String>().apply {
        add(junit)
        add(coroutinesTest)
    }

    //android test
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val appAndroidTest = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
        add(coroutinesTest)
    }

    //truth
    private const val truth = "com.google.truth:truth:${Versions.truth}"
    val appTruth get() = truth

    private const val unitMockito = "org.mockito:mockito-core:${Versions.mockito}"
    private const val androidMockito = "org.mockito:mockito-core:${Versions.mockito}"
    private const val inlineMockito = "org.mockito:mockito-core:${Versions.mockito}"

    val appMockito = Requirement(
        testImplementations = arrayListOf<String>().apply {
            add(unitMockito)
            add(inlineMockito)
        },
        androidTestImplementations = arrayListOf<String>().apply {
            add(androidMockito)
        }
    )

    //robolectric
    private const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    val appRobolectric get() = robolectric
}