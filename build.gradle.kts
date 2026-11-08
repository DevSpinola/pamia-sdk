plugins {
    id("com.android.library") version "8.11.1"
    id("org.jetbrains.kotlin.android") version "2.2.21"
    `maven-publish`
}

group = "com.github.DevSpinola"
// VERSION é a tag fornecida pelo JitPack; localmente usamos uma versão de desenvolvimento.
version = providers.environmentVariable("VERSION").orElse("0.1.0-SNAPSHOT").get()

android {
    namespace = "br.com.pamia.sdk"
    compileSdk = 36
    buildToolsVersion = "35.0.0"
    defaultConfig { minSdk = 24 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    publishing { singleVariant("release") }
}

kotlin { jvmToolchain(17) }

// O componente release só fica disponível depois da configuração do plugin Android.
afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])
                artifactId = "pamia-sdk"
                pom {
                    name.set("PAMIA SDK")
                    description.set("Estrutura inicial da SDK Android do PAMIA, sem detecção.")
                    url.set("https://github.com/DevSpinola/pamia-sdk")
                }
            }
        }
    }
}
