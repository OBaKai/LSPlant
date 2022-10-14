plugins {
    id("com.android.library")
}

val androidTargetSdkVersion: Int by rootProject.extra
val androidMinSdkVersion: Int by rootProject.extra
val androidBuildToolsVersion: String by rootProject.extra
val androidCompileSdkVersion: Int by rootProject.extra
val androidNdkVersion: String by rootProject.extra
val androidCmakeVersion: String by rootProject.extra

android {
    namespace = "org.lsposed.lsplant.test"
    compileSdk = androidCompileSdkVersion
    ndkVersion = androidNdkVersion
    buildToolsVersion = androidBuildToolsVersion

    buildFeatures {
        buildConfig = false
        prefab = true
    }

    defaultConfig {
        //applicationId = "org.lsposed.lsplant"
        minSdk = androidMinSdkVersion
        targetSdk = androidTargetSdkVersion
        //versionCode = 1
        //versionName = "1.0"
        externalNativeBuild {
            cmake {
                arguments += "-DANDROID_STL=c++_shared"
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/jni/CMakeLists.txt")
            version = androidCmakeVersion
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
//    implementation(project(":lsplant"))
    implementation("org.lsposed.lsplant:lsplant:5.0")
    implementation("io.github.vvb2060.ndk:dobby:1.2")
}
