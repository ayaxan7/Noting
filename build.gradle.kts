plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
}

tasks.register<Delete>("clean") {
    delete(project.layout.buildDirectory)
}
