import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.8"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"

    //Hibernate는 지연로딩을 위해 Entity들을 상속하여 프록시를 만드는데 코틀린에서는 클래스의 기본 상속 제어자가 final이기 때문에
    //지연로딩으로 설정해도 프록시를 만들지 못해 지연로딩이 되지 않는 문제가 발생
    //이를 해결해주는 allopen plugin -> 모든 entity에 open 시켜준다.
    kotlin("plugin.spring") version "1.6.21" //kotlin("plugin.allopen") 포함

    //kotlin("plugin.noarg") 포함 -> JPA 개발 시 JPA Entity들은 기본적으로 인자 없는 기본생성자가 필요함
    //하지만 코틀린에서 주생성자가 있다면 기본생성자가 없어서 만들어줘야 하는데 프로퍼티가 많은 경우 생성함의 번거로움이 생김
    //noarg plugin을 추가하면 @Entity가 붙은 클래스들에 한해서 자동으로 인자없는 생성자를 추가해줌.
    kotlin("plugin.jpa") version "1.6.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

//all-open
allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-web")

    //gson
    implementation("com.google.code.gson:gson:2.8.8")

    //SXSSFWorkbook
    implementation("org.apache.poi:poi:5.0.0")
    implementation("org.apache.poi:poi-ooxml:5.0.0")

    //StringUtils
    implementation("org.apache.commons:commons-lang3:3.12.0")

    implementation("commons-io:commons-io:2.8.0")

    implementation("eu.bitwalker:UserAgentUtils:1.21")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")


    //jpa, mysql
    implementation("com.vladmihalcea:hibernate-types-52:2.12.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.27")
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
