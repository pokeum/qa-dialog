object Versions {

    private const val MAJOR_NUMBER = 1
    private const val MINOR_NUMBER = 1
    private const val PATCH_NUMBER = 0

    const val VERSION_CODE = MAJOR_NUMBER * 1_000_000 + MINOR_NUMBER * 1_000 + PATCH_NUMBER
    const val VERSION_NAME = "$MAJOR_NUMBER.$MINOR_NUMBER.$PATCH_NUMBER"

    const val ANDROID_GRADLE_PLUGIN = "4.2.2"
    const val KOTLIN_PLUGIN = "1.6.10"

    const val BUILD_TOOLS = "30.0.3"
    const val COMPILE_SDK = 31
    const val TARGET_SDK = 31
    const val MIN_SDK = 16

    const val KOTLIN = "1.4.0"

    const val ANDROID_MATERIAL = "1.6.0"
    const val ANDROIDX_CONSTRAINT_LAYOUT = "1.0.1"
    const val ANDROIDX_APPCOMPAT = "1.4.1"

    const val JUNIT = "4.13"

    const val JSON = "20180813"
}