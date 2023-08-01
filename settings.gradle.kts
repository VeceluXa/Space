pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
    }
    versionCatalogs {
        create("libs") {
            // RxJava
            library("rxandroid", "io.reactivex.rxjava3", "rxandroid").version("3.0.2")
            library("rxjava", "io.reactivex.rxjava3", "rxjava").version("3.1.6")
            bundle("rxjava", listOf("rxandroid", "rxjava"))
            // Hilt
            version("hilt", "2.45")
            library("hilt-android", "com.google.dagger", "hilt-android").versionRef("hilt")
            library("hilt-compiler", "com.google.dagger", "hilt-compiler").versionRef("hilt") // Use kapt
        }
    }
}

rootProject.name = "Space"
include(":app")
include(":data")
include(":common")
