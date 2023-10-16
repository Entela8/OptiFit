import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.optifit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.optifit"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    fun Packaging.() {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,DEPENDENCIES}"
        }
    }
}

dependencies {
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("androidx.activity:activity-compose:1.8.0")
    implementation (platform("androidx.compose:compose-bom:1.2.2"))
    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.10.0")
    implementation ("androidx.annotation:annotation:1.7.0")
    implementation ("androidx.compose.ui:ui-graphics")
    implementation ("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.compose.ui:ui-tooling:1.5.3")
    implementation ("androidx.compose.material:material:1.5.3")

    implementation("androidx.compose:compose-bom:2023.10.00")

    implementation ("com.fasterxml.jackson.core:jackson-core:2.13.0")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.13.0")
    implementation ("com.fasterxml.jackson.core:jackson-annotations:2.13.0")

    implementation ("com.google.http-client:google-http-client-gson:1.42.0")
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("pub.devrel:easypermissions:3.0.0")
    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation ("com.google.apis:google-api-services-youtube:v3-rev205-1.31.3")

    implementation ("com.google.api-client:google-api-client:1.31.3")
    implementation ("com.google.oauth-client:google-oauth-client-jetty:1.34.1")

    implementation ("androidx.recyclerview:recyclerview:1.3.1")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.android.car.ui:car-ui-lib:2.5.1")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation (platform("androidx.compose:compose-bom:1.2.2"))
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4")
    debugImplementation ("androidx.compose.ui:ui-tooling")
    debugImplementation ("androidx.compose.ui:ui-test-manifest")
}
