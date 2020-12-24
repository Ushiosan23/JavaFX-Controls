plugins {
	signing
	`java-library`
	`maven-publish`
	id("org.openjfx.javafxplugin") version "0.0.9"
}

// Library configuration
group = "com.github.ushiosan23"
version = "0.0.1"

// Configure repositories
repositories {
	mavenCentral()
	jcenter()
}

// Configure Javafx
javafx {
	version = "15.0.1"
	modules = arrayListOf(
		"javafx.base",
		"javafx.controls",
		"javafx.fxml",
		"javafx.graphics",
		"javafx.media",
		"javafx.swing",
		"javafx.web"
	)
}

// Configure java plugin
java {
	withJavadocJar()
	withSourcesJar()
}

// Configure dependencies
dependencies {
	/* basic dependencies */
	implementation("org.jetbrains:annotations:19.0.0")
	/* test */
	implementation("junit", "junit", "4.13")
}

/* ---------------------------------------------------------
 *
 * Publishing configuration
 *
 * --------------------------------------------------------- */

publishing {
	val stringVersion = rootProject.version as String
	val versionName = stringVersion.replace(".", "_")

	/* Configure publications */
	publications {

		/* Create maven publication */
		create<MavenPublication>("mavenJava") {
			groupId = rootProject.group as String
			artifactId = rootProject.name.toLowerCase()
			version = rootProject.version as String

			/* Set java components */
			from(components["java"])

			/* POM document */
			pom {
				name.set("${rootProject.name}_$versionName")
				description.set("A library with javafx controls to use in any project.")
				url.set("https://github.com/Ushiosan23/JavaFXControls")

				// License
				licenses {
					license {
						name.set("MIT License")
						url.set("https://raw.githubusercontent.com/Ushiosan23/JavaFXControls/master/LICENSE")
					}
				}
				developers {
					developer {
						id.set("Ushiosan23")
						name.set("Ushiosan23")
						email.set("haloleyendee@outlook.com")
					}
				}
				scm {
					connection.set("scm:git:git://github.com/Ushiosan23/JavaFXControls.git")
					developerConnection.set("scm:git:ssh://github.com/Ushiosan23/JavaFXControls.git")
					url.set("https://github.com/Ushiosan23/JavaFXControls")
				}
			}

			/* Dependencies */
			versionMapping {
				usage("java-api") {
					fromResolutionOf("runtimeClasspath")
				}
				usage("java-runtime") {
					fromResolutionResult()
				}
			}
		}

	}

	/* Define repositories */
	repositories {
		/* maven repositories */
		maven {
			val targetRepoURL = if (stringVersion.endsWith("SNAPSHOT"))
				"https://oss.sonatype.org/content/repositories/snapshots"
			else
				"https://oss.sonatype.org/service/local/staging/deploy/maven2"

			name = "MavenCentralRepository"
			url = uri(targetRepoURL)

			/* credentials */
			credentials {
				username = rootProject.properties["mavenUser"] as String? ?: ""
				password = rootProject.properties["mavenPass"] as String? ?: ""
			}
		}
	}
}

signing {
	sign(publishing.publications["mavenJava"])
}

/* ---------------------------------------------------------
 *
 * Configure tasks
 *
 * --------------------------------------------------------- */

tasks.javadoc {
	if (JavaVersion.current().isJava9Compatible) {
		(options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
	}
}
