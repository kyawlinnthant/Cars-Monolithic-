data class Requirement(
    val implementations: List<String>? = null,
    val kapts: List<String>? = null,
    val annotationProcessors: List<String>? = null,
    val testImplementations: List<String>? = null,
    val androidTestImplementations: List<String>? = null,
    val debugImplementations: List<String>? = null,
    val kaptAndroidTests: List<String>? = null,
    val kaptTests : List<String>? = null,
)
