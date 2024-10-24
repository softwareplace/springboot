target ?= 8.5
tag ?= 1.0.0-SNAPSHOT

test-security:
	cd libs/security/example && make test

test:
	make publish-version tag=1.0.0
	cd example && make test
	make test-security

example-test:
	cd example && make test

gradle-wrapper:
	./gradle wrapper --gradle-version=$(target)

publish:
	./localPublish

publish-version:
	./installer $(tag)
