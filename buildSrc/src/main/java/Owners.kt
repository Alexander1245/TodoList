interface Owner

interface ImplementationsOwner : Owner {
    val implementations: List<String>
}

interface KaptsOwner : Owner {
    val kapts: List<String>
}

interface AppPluginOwner : Owner {
    val plugins: List<String>
}

interface TopLevelPluginOwner : Owner {
    val topLevelPlugins: List<Pair<String, String>>
}

interface TopLevelKotlinPluginOwner : Owner {
    val topLevelKotlinPlugins: List<Pair<String, String>>
}

interface KotlinAppPluginOwner : Owner {
    val kotlinPlugins: List<String>
}

interface KotlinImplementationsOwner : Owner {
    val kotlinImplementations: List<String>
}

interface TestImplementationsOwner : Owner {
    val testImplementations: List<String>
}

interface AndroidTestImplementationsOwner : Owner {
    val androidTestImplementations: List<String>
}

interface TopLevelClasspathOwner : Owner {
    val topLevelClasspaths:List<String>
}

interface DependenciesOwner : ImplementationsOwner, KaptsOwner, KotlinImplementationsOwner,
    AppPluginOwner, KotlinAppPluginOwner, TestImplementationsOwner, AndroidTestImplementationsOwner,
        TopLevelPluginOwner, TopLevelKotlinPluginOwner, TopLevelClasspathOwner