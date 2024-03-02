SHELL := bash


.PHONY: all
all: build


.PHONY: build
build:
	./gradlew build


.PHONY: wrapper
wrapper:
	./gradlew wrapper
