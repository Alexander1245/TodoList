pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    plugins {
        val androidLibraryVersion: String by settings
        id("com.android.application") version androidLibraryVersion
        id("com.android.library") version androidLibraryVersion

        val kotlinVersion: String by settings
        kotlin("android") version kotlinVersion
    }
}
dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Todo List"
include(":app")
