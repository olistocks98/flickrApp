// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    alias(libs.plugins.compose.compiler) apply false
    kotlin("plugin.power-assert") version "2.0.0"
}
powerAssert {
    functions = listOf("kotlin.assert", "kotlin.test.assertTrue")
}