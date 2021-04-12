#!/bin/sh

cd grpc-service

chmod +x gradlew

./gradlew build

docker build -t sarouar1/grpc-service:latest .

docker push sarouar1/grpc-service:latest