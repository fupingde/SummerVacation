pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SummerVacation"
include(":app")
include(":lib_wangyiyun")
include(":module_main")
include(":module_main:api_base")
include(":Lgs_moudule_drawerView")
include(":Lgs_module_login")
include(":module_search")
include(":module_broadcast")
