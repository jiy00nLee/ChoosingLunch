import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.30"
	kotlin("plugin.spring") version "1.4.30"
}

group = "com.example"
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
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")	//동적 템플릿(타임리프) 라이브러리 추가
	//implementation("org.springframework.boot:spring-boot-starter-jdbc")	//순수 jdbc 사용 라이브러리 추가
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa") //Jpa 라이브러리 추가
	runtimeOnly ("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	//retrofit & okhttp 사용.
	implementation ("com.squareup.retrofit2:retrofit:2.3.0")
	implementation("com.squareup.retrofit2:converter-gson:2.3.0")
	implementation ("com.squareup.okhttp3:logging-interceptor:3.9.0")

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
