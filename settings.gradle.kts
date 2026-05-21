pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RecordFitness"
include(":app")
