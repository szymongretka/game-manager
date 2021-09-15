#!/bin/bash

echo "Performing a clean Maven build"
./mvnw clean package -DskipTests=true

echo "Building the Eureka server"
cd eureka-server
docker build --tag eureka-server .
cd ..

echo "Building the UAA"
cd uaa
docker build --tag scg-demo-uaa .
cd ..

echo "Building the Gateway"
cd api-gateway
docker build --tag api-gateway .
cd ..

echo "Building the Data provider"
cd data-provider-service
docker build --tag data-provider-service .
cd ..

echo "Building the App provider"
cd app-provider-service
docker build --tag app-provider-service .
cd ..