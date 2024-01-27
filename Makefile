target ?= 8.5
tag ?= 1.0.0-SNAPSHOT
test:
	./gradlew clean test

gradle-wrapper:
	./gradle wrapper --gradle-version=$(target)

publish:
	gradle \
	build-configuration:assemble build-configuration:publishToMavenLocal \
	java:assemble java:publishToMavenLocal \
	kotlin:assemble kotlin:publishToMavenLocal \
	assemble publishToMavenLocal --refresh-dependencies -Pversion=$(tag)
