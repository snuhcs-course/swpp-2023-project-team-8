// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
    id("io.github.gmazzo.test.aggregation.coverage") version "2.1.1"
    id("com.google.dagger.hilt.android") version "2.44" apply false
}