target ?= 8.5
tag ?= 1.0.0-SNAPSHOT

test:
	make preinstall
	./gradlew clean test

gradle-wrapper:
	./gradle wrapper --gradle-version=$(target)

preinstall:
	./installer

publish:
	make preinstall
	gradle assemble publishToMavenLocal -Pgroup=com.github.softwareplace --refresh-dependencies
	./postinstaller

publish-version:
	make preinstall
	gradle assemble publishToMavenLocal -Pgroup=com.github.softwareplace --refresh-dependencies -Pversion=$(tag)
	pwd
	ls -lah runner/build

libs-build:
	make preinstall
	gradle assemble build -Pgroup=com.github.softwareplace --refresh-dependencies -Pversion=$(tag)
