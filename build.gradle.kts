// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply{
        set("compileSdk", 34)
        set("hiltVersion", "2.47")
        set("gsonVersion", "2.10.1")
        set("retrofitVersion", "2.9.0")
        set("okhttpVersion", "4.10.0")
        set("chuckerVersion", "4.0.0")
    }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${rootProject.extra["hiltVersion"]}")
        classpath("com.google.gms:google-services:4.4.0")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
    }
}
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.android.library") version "8.1.1" apply false
    id("com.google.dagger.hilt.android") version "2.47" apply false
    id("androidx.navigation.safeargs") version "2.5.0" apply false
}