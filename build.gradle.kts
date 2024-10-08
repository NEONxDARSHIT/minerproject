buildscript {
    dependencies {
        classpath ("com.android.tools.build:gradle:7.3.0")

        classpath ("com.google.gms:google-services:4.4.0")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.2" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}
