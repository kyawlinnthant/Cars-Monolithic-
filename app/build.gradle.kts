plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.sevenpeakssoftware.kyawlinnthant"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.sevenpeakssoftware.kyawlinnthant"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        testInstrumentationRunner = "com.sevenpeakssoftware.kyawlinnthant.CarsTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            type = "String",
            name = "BASE_URL",
            value = "\"https://cars-sevenpeaks.web.app/\""
        )
    }
    buildTypes {
        getByName("release") {
            // Enables code shrinking, obfuscation, and optimization
            isMinifyEnabled = true
            // Enables resource shrinking, which is performed by the Android Gradle plugin.
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }

    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {
    testImplementation(project(mapOf("path" to ":test-rule")))
    androidTestImplementation(project(mapOf("path" to ":test-rule")))
    //app dependencies
    this implements Dependencies.appDependencies
    this implement Dependencies.appMaterial
    this implement Dependencies.appSplash
    this implement Dependencies.appSystemUi
    this implement Dependencies.appSwifeRefresh
    this implement Dependencies.appCoil
    this implement Dependencies.appTimber
    this implement Dependencies.appMultidex
    this implement Dependencies.appRobolectric
    //compose
    this needs Dependencies.appCompose
    this implement Dependencies.appNavigation
    //di
    this needs Dependencies.appDi
    //network
    this needs Dependencies.appNetwork
    //db
    this needs Dependencies.appDb
    //pref
    this implement Dependencies.appPref

    //android test
    this androidTests Dependencies.appAndroidTest
    //unit test
    this unitTests Dependencies.appUnitTest
    //google truth
    this unitTest Dependencies.appTruth
    this androidTest Dependencies.appTruth
    this needs Dependencies.appMockito

    coreLibraryDesugaring(Dependencies.appDesugar)

}
kapt {
    correctErrorTypes = true
}