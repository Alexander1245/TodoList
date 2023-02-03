object Android : AppPluginOwner, ImplementationsOwner, TopLevelPluginOwner {
    private const val CORE_KTX_VERSION = "1.9.0"
    private const val APPCOMPAT_VERSION = "1.5.1"
    private const val LEGACY_SUPPORT_VERSION = "1.0.0"

    private const val ANDROID_LIB_VERSION = "7.4.0"

    override val topLevelPlugins: List<Pair<String, String>> = listOf(
        "com.android.application" to ANDROID_LIB_VERSION,
        "com.android.library" to ANDROID_LIB_VERSION,
    )

    override val plugins = listOf(
        "com.android.application",
    )

    override val implementations: List<String> = listOf(
        "androidx.core:core-ktx:$CORE_KTX_VERSION",
        "androidx.appcompat:appcompat:$APPCOMPAT_VERSION",
        "androidx.legacy:legacy-support-v4:$LEGACY_SUPPORT_VERSION"
    )
}

object Material : ImplementationsOwner {
    private const val VERSION = "1.8.0"

    override val implementations: List<String> = listOf(
        "com.google.android.material:material:$VERSION",
    )
}

object ConstraintLayout : ImplementationsOwner {
    private const val VERSION = "2.1.4"

    override val implementations: List<String> = listOf(
        "androidx.constraintlayout:constraintlayout:$VERSION"
    )
}

object RecyclerView : ImplementationsOwner {
    private const val VERSION = "1.2.1"

    override val implementations: List<String> = listOf(
        "androidx.recyclerview:recyclerview:$VERSION",
    )
}

object Kotlin : KotlinImplementationsOwner, KotlinAppPluginOwner, TopLevelKotlinPluginOwner {
    private const val VERSION = "1.7.20"

    override val topLevelKotlinPlugins: List<Pair<String, String>> = listOf(
        "android" to VERSION,
    )

    override val kotlinPlugins = listOf(
        "android",
        "kapt",
    )

    override val kotlinImplementations = listOf(
        "reflect"
    )
}

object Hilt : ImplementationsOwner, KaptsOwner, AppPluginOwner, TopLevelPluginOwner {
    private const val VERSION = "2.44"
    private const val PLUGIN = "com.google.dagger.hilt.android"

    override val topLevelPlugins: List<Pair<String, String>> = listOf(
        PLUGIN to VERSION,
    )

    override val plugins: List<String> = listOf(
        PLUGIN,
    )

    override val implementations = listOf(
        "com.google.dagger:hilt-android:$VERSION",
    )

    override val kapts = listOf(
        "com.google.dagger:hilt-android-compiler:$VERSION",
    )
}

object SafeArgs : TopLevelClasspathOwner, AppPluginOwner {
    private const val VERSION = Navigation.VERSION

    override val plugins: List<String> = listOf(
        "androidx.navigation.safeargs.kotlin",
    )

    override val topLevelClasspaths: List<String> = listOf(
        "androidx.navigation:navigation-safe-args-gradle-plugin:$VERSION",
    )
}

object Navigation : ImplementationsOwner {
    const val VERSION = "2.5.3"

    override val implementations = listOf(
        "androidx.navigation:navigation-fragment-ktx:$VERSION",
        "androidx.navigation:navigation-ui-ktx:$VERSION",
    )
}

object Lifecycle : ImplementationsOwner {
    private const val VERSION = "2.5.1"

    override val implementations = listOf(
        "androidx.lifecycle:lifecycle-livedata-ktx:$VERSION",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:$VERSION",
    )
}

object DataStore : ImplementationsOwner {
    private const val VERSION = "1.0.0"

    override val implementations = listOf(
        "androidx.datastore:datastore-preferences:$VERSION",
    )
}

object JUnit : TestImplementationsOwner, AndroidTestImplementationsOwner {
    private const val UNIT_TEST_VERSION = "4.13.2"
    private const val ANDROID_TEST_VERSION = "1.1.5"

    override val testImplementations: List<String> = listOf(
        "junit:junit:$UNIT_TEST_VERSION",
    )

    override val androidTestImplementations: List<String> = listOf(
        "androidx.test.ext:junit:$ANDROID_TEST_VERSION"
    )
}

object Espresso : AndroidTestImplementationsOwner {
    private const val VERSION = "3.5.1"

    override val androidTestImplementations: List<String> = listOf(
        "androidx.test.espresso:espresso-core:$VERSION",
    )
}

object Coroutines : TestImplementationsOwner, ImplementationsOwner {
    private const val VERSION = "1.3.9"

    override val implementations: List<String> = listOf(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:$VERSION",
    )

    override val testImplementations: List<String> = listOf(
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:$VERSION",
    )
}

object Room : ImplementationsOwner, KaptsOwner {
    private const val VERSION = "2.5.0"

    override val kapts: List<String> = listOf(
        "androidx.room:room-compiler:$VERSION",
    )

    override val implementations: List<String> = listOf(
        "androidx.room:room-runtime:$VERSION",
        "androidx.room:room-ktx:$VERSION",
        "androidx.room:room-paging:$VERSION",
    )
}

object Paging : ImplementationsOwner, TestImplementationsOwner {
    private const val VERSION = "3.1.1"

    override val implementations: List<String> = listOf(
        "androidx.paging:paging-runtime:$VERSION",
    )

    override val testImplementations: List<String> = listOf(
        "androidx.paging:paging-common:$VERSION",
    )
}

object Parcelize : AppPluginOwner {
    override val plugins: List<String> = listOf(
        "kotlin-parcelize"
    )
}