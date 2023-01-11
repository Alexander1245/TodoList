object Android : AppPluginOwner, ImplementationsOwner, TopLevelPluginOwner {
    private const val CORE_KTX_VERSION = "1.9.0"
    private const val APPCOMPAT_VERSION = "1.5.1"
    private const val LEGACY_SUPPORT_VERSION = "1.0.0"

    private const val ANDROID_LIB_VERSION = "7.3.1"

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
    private const val VERSION = "1.8.0-rc01"

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

    override val topLevelPlugins: List<Pair<String, String>> = listOf(
        "com.google.dagger.hilt.android" to VERSION,
    )

    override val plugins: List<String> = listOf(
        "com.google.dagger.hilt.android",
    )

    override val implementations = listOf(
        "com.google.dagger:hilt-android:$VERSION",
    )

    override val kapts = listOf(
        "com.google.dagger:hilt-android-compiler:$VERSION",
    )
}

object Navigation : ImplementationsOwner {
    private const val VERSION = "2.5.3"

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
