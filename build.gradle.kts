plugins {
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.5.0"
    id("com.google.cloud.tools.jib") version "2.8.0"
}

version = "0.1"
group = "com.barddoo"

repositories {
    mavenCentral()
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.barddoo.*")
    }
}

dependencies {
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("io.micronaut.jaxrs:micronaut-jaxrs-processor")
    annotationProcessor("io.micronaut.openapi:micronaut-openapi")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.jaxrs:micronaut-jaxrs-server")
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("io.micronaut.mongodb:micronaut-mongo-reactive")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("javax.annotation:javax.annotation-api")
    implementation("org.apache.logging.log4j:log4j-core:2.13.3")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.apache.logging.log4j:log4j-api:2.13.3")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.13.2")
    testAnnotationProcessor("io.micronaut.jaxrs:micronaut-jaxrs-processor")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mongodb")
    testImplementation("org.testcontainers:testcontainers")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut.rxjava3:micronaut-rxjava3")
    testImplementation("org.hamcrest:hamcrest")
}


application {
    mainClass.set("com.barddoo.Application")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
    targetCompatibility = JavaVersion.toVersion("11")
}



jib {
    to {
        image = "b2w-star-wars"
    }
}
