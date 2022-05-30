import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "com.oyee-taxi"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.projectlombok:lombok:1.18.24")

	implementation("junit:junit:4.13.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	//runtimeOnly("mysql:mysql-connector-java") No Used

	implementation("com.twilio.sdk:twilio:8.29.1")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-security")
	testImplementation("org.springframework.security:spring-security-test")
	implementation("org.springframework.boot:spring-boot-starter-mail")

	implementation("io.socket:socket.io-server:4.0.1")
	implementation("org.springframework.boot:spring-boot-starter-websocket")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}


//OLD
//plugins {
//	id("org.springframework.boot") version "2.6.2"
//	id("io.spring.dependency-management") version "1.0.11.RELEASE"
//	kotlin("jvm") version "1.6.10"
//	kotlin("plugin.spring") version "1.6.10"
//	kotlin("plugin.jpa") version "1.6.10"
//}
//    implementation("org.projectlombok:lombok:1.18.20")
//    implementation("junit:junit:4.13.1")
//	implementation("com.twilio.sdk:twilio:8.26.0")