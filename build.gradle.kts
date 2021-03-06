buildscript {
	repositories {
		jcenter()
		mavenCentral()
	}
	dependencies {
		classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:0.6")
	}
}

plugins {
	kotlin("jvm") version "1.2.41"
	`maven-publish`
}

//apply plugin: 'groovy'
//apply plugin: 'signing'
//apply plugin: 'com.jfrog.bintray'

// Release version that won't conflict with the bintray plugin
group = "org.liquibase"
version = "1.2.2"

configurations {
	"archives"()
}

repositories {
	mavenCentral()
	mavenLocal()
}

dependencies {
	compile(kotlin("compiler"))
	compile(kotlin("script-util"))
	compile("org.liquibase:liquibase-core:3.4.2")
	testCompile("junit:junit:4.12")
	testCompile(kotlin("test"))
	testRuntime("com.h2database:h2:1.4.185")
	archives("org.apache.maven.wagon:wagon-ssh:2.8")
	archives("org.apache.maven.wagon:wagon-ssh-external:2.8")
}

tasks {
	"sourceJar"(Jar::class) {
		description = "An archive of the source code for Maven Central"
		classifier = "sources"
		from(java.sourceSets["main"].allSource)
	}

	getting(Wrapper::class) {
		gradleVersion = "4.7"
	}
}
/*
artifacts {
    archives jar, sourceJar
}

signing {
    sign configurations.archives
}

// Only *Require* signing if we are uploading a non snapshot version.  If we
// do need to sign, make sure we've got the properties we need to do the
// signing.
gradle.taskGraph.whenReady { taskGraph ->
    tasks.withType(Sign).all {
        required = taskGraph.hasTask(':uploadArchives') && !isSnapshot
        if (required) {
            // Use Java's console to read from the console (no good for a CI
            // environment)
            Console console = System.console()
            console.printf '\n\nWe have to sign some things in this build...\n\n'

            if (!project.hasProperty('signing.keyId')) {
                def id = console.readLine('PGP Public Key Id: ')
                allprojects { ext.'signing.keyId' = id }
            }

            if (!project.hasProperty('signing.secretKeyRingFile')) {
                def file = console.readLine('PGP Secret Key Ring File (absolute path): ')
                allprojects { ext.'signing.secretKeyRingFile' = file }
            }

            if (!project.hasProperty('signing.password')) {
                def password = console.readPassword('PGP Private Key Password: ')
                allprojects { ext.'signing.password' = password }
            }

            console.printf '\nThanks.\n\n'
        }
    }
}

uploadArchives {
    // We can't use taskGraph.whenReady because it doesn't resolve until after
    // configuration.  The startParameter is not as good, but it probably
    // good enough for our purposes.
    if (gradle.startParameter.taskNames.contains('uploadArchives')) {
        // Use Java's console to read from the console (no good for a CI
        // environment)
        Console console = System.console()
        console.printf '\n\nWe have to upload some things in this build...\n\n'

        if (!project.hasProperty('mavenCentralUsername')) {
            def mavenCentralUsername = console.readLine('Maven Central Username: ')
            allprojects { ext.'mavenCentralUsername' = mavenCentralUsername }
        }

        if (!project.hasProperty('mavenCentralPassword')) {
            def mavenCentralPassword = console.readLine('Maven Central Password: ')
            allprojects { ext.'mavenCentralPassword' = mavenCentralPassword }
        }

        repositories {
            mavenDeployer {
                if (signing.signatory) {
                    beforeDeployment { signing.signPom(it) }
                }
                configuration = configurations.archives
                //repository(url: 'file://$buildDir/m2repo')
                repository(url: mavenCentralUploadUrl) {
                    authentication(userName: mavenCentralUsername,
                            password: mavenCentralPassword)
                    releases(updatePolicy: 'always')
                    snapshots(updatePolicy: 'always')
                }
                pom.project(pomConfiguration)
            }
        }
    }
}

def getPomConfiguration() {
    return {
        name('Kotlin Liquibase DSL')
        description('A Kotlin-based DSL for the Liquibase database refactoring tool.')
        url('https://github.com/redundent/liquibase-kotlin-dsl')
        licenses {
            license {
                name 'The Apache Software License, Version 2.0'
                url 'http://www.apache.org/licenses/LICENSE-2.0.txt'
                distribution 'repo'
            }
        }
        developers {
            developer {
                id 'redundent'
                name 'Jason Blackwell'
                email 'redundent@gmail.com'
            }
        }
    }
}*/

//    publishing {
//        repositories {
//            mavenLocal()
//        }
//        publications {
//            maven(MavenPublication) {
//                from components.java
//
//                        artifact sourceJar {
//                    classifier 'sources'
//                }
//            }
//        }
//    }
//}