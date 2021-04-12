#!/bin/sh

cd gateway

chmod +x gradlew

./gradlew build

docker build -t sarouar1/gateway:latest .

docker push sarouar1/gateway:latest