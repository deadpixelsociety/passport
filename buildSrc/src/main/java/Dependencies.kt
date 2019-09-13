object App {
    const val version_code = 5
    const val version_name = "2.2"
    const val group = "com.github.deadpixelsociety"
}

object Android {
    const val build_tools = "29.0.2"
    const val min_sdk = 14
    const val target_sdk = 29
    const val compile_sdk = 29
}

object Versions {
    const val kotlin = "1.3.50"
    const val maven = "1.5"

    const val android_gradle = "3.5.0"
    const val android_support = "28.0.0"

    const val androidx_material = "1.1.0-alpha10"
    const val androidx_fragment = "1.1.0"
}

object Deps {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val android_support_fragment = "com.android.support:support-fragment:${Versions.android_support}"
    const val android_support_design = "com.android.support:design:${Versions.android_support}"

    const val androidx_material = "com.google.android.material:material:${Versions.androidx_material}"
    const val androidx_fragment = "androidx.fragment:fragment:${Versions.androidx_fragment}"
}