target ?= 8.5
tag ?= 1.0.0

test:
	./gradlew clean test

javaExampleRun:
	./gradlew java-example:bootRun

kotlinExampleRun:
	./gradlew kotlin-example:bootRun

webfluxExampleRun:
	./gradlew webflux-example:bootRun

module-test:
	./gradlew $(module):clean $(module):test
