object AppDependencies : DependenciesOwner {
    private val owners = listOf<Owner>(
        Kotlin,
        Hilt,
        Lifecycle,
        DataStore,
        Android,
        Navigation,
        Material,
        ConstraintLayout,
        JUnit,
        Espresso,
        Coroutines,
        SafeArgs,
    )

    override val implementations: List<String> =
        owners.filterIsInstance(ImplementationsOwner::class.java).flatMap {
            it.implementations
        }

    override val kapts: List<String> =
        owners.filterIsInstance(KaptsOwner::class.java).flatMap {
            it.kapts
        }

    override val kotlinImplementations: List<String> =
        owners.filterIsInstance(KotlinImplementationsOwner::class.java).flatMap {
            it.kotlinImplementations
        }

    override val plugins: List<String> =
        owners.filterIsInstance(AppPluginOwner::class.java).flatMap {
            it.plugins
        }

    override val kotlinPlugins: List<String> =
        owners.filterIsInstance(KotlinAppPluginOwner::class.java).flatMap {
            it.kotlinPlugins
        }

    override val testImplementations: List<String> =
        owners.filterIsInstance(TestImplementationsOwner::class.java).flatMap {
            it.testImplementations
        }

    override val androidTestImplementations: List<String> =
        owners.filterIsInstance(AndroidTestImplementationsOwner::class.java).flatMap {
            it.androidTestImplementations
        }

    override val topLevelPlugins: List<Pair<String, String>> =
        owners.filterIsInstance(TopLevelPluginOwner::class.java).flatMap {
            it.topLevelPlugins
        }

    override val topLevelKotlinPlugins: List<Pair<String, String>> =
        owners.filterIsInstance(TopLevelKotlinPluginOwner::class.java).flatMap {
            it.topLevelKotlinPlugins
        }

    override val topLevelClasspaths: List<String> =
        owners.filterIsInstance(TopLevelClasspathOwner::class.java).flatMap {
            it.topLevelClasspaths
        }
}