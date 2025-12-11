plugins {
    kotlin("jvm") version "2.2.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kandy-lets-plot:0.8.0")
}

tasks {
    wrapper {
        gradleVersion = "9.2.1"
    }
}
