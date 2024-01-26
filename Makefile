target ?= 8.5

test:
	cd example && ./gradlew clean test

gradle-wrapper:
	cd example && ./gradle wrapper --gradle-version=$(target)
