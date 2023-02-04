plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }

    }
    compileOptions {
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
}