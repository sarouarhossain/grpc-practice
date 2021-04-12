#!/bin/sh

cd rest-service

chmod +x gradlew

./gradlew build

docker build -t sarouar1/rest-service:latest .

docker push sarouar1/rest-service:latest