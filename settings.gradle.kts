pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io") }
    }
}
rootProject.name = "WedateCompose"
include(":app")
include(":Common")
include(":feature")
include(":feature:auth")
include(":feature:account")
include(":feature:admirers")
include(":feature:matches")
include(":feature:home")
include(":feature:lovecalculator")
include(":feature:settings")
include(":feature:profiledetails")
include(":feature:profileedit")
include(":feature:chatsscreen")
include(":feature:maps")
include(":Network")
include(":feature:explore")
