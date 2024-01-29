target ?= 8.5
tag ?= 1.0.0-SNAPSHOT

test:
	./gradlew clean test
	cd example && make test

gradle-wrapper:
	./gradle wrapper --gradle-version=$(target)

publish:
	./localPublish

publish-version:
	./installer $(tag)
