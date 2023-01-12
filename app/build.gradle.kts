plugins {
    AppDependencies.kotlinPlugins.forEach { kotlin(it) }
    AppDependencies.plugins.forEach { id(it) }
}

android {
    namespace = "com.dart69.todolist"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.dart69.todolist"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
    }
}

dependencies {
    AppDependencies.kapts.forEach {
        kapt(it)
    }

    AppDependencies.implementations.forEach {
        implementation(it)
    }

    AppDependencies.kotlinImplementations.forEach {
        implementation(kotlin(it))
    }

    AppDependencies.testImplementations.forEach {
        testImplementation(it)
    }

    AppDependencies.androidTestImplementations.forEach {
        androidTestImplementation(it)
    }
}

kapt {
    correctErrorTypes = true
}