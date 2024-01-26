target ?= 8.5

test:
	./gradlew clean test

gradle-wrapper:
	./gradle wrapper --gradle-version=$(target)
