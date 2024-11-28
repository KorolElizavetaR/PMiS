plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.devtoolsKsp)
    alias(libs.plugins.hiltPlugin) apply false
}

//buildscript {
//    dependencies {
//        classpath("com.google.dagger:hilt-android-gradle-plugin:<version>")
//    }
//}



