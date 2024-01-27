target ?= 8.5
tag ?= 1.0.0-SNAPSHOT

test:
	make preinstall
	./gradlew clean test

gradle-wrapper:
	./gradle wrapper --gradle-version=$(target)

publish:
	./installer
	./postinstaller

publish-version:
	make preinstall
	./installer $(tag)
	ls -lah runner/build

libs-build:
	make preinstall
	gradle assemble build -Pgroup=com.github.softwareplace --refresh-dependencies -Pversion=$(tag)
