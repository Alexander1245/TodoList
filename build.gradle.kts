// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    AppDependencies.topLevelPlugins.forEach { (id, version) ->
        id(id) version version apply false
    }
    AppDependencies.topLevelKotlinPlugins.forEach { (id, version) ->
        kotlin(id) version version apply false
    }
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        AppDependencies.topLevelClasspaths.forEach {
            classpath(it)
        }
    }
}

