#!/bin/bash

echo "Building the Eureka"
cd eureka-server
./mvnw clean package -DskipTests=true
docker build --tag eureka-server .
cd ..

echo "Building the API Gateway"
cd api-gateway
./mvnw clean package -DskipTests=true
docker build --tag api-gateway .
cd ..

echo "Building the Data Service"
cd data-provider-service
./mvnw clean package -DskipTests=true
docker build --tag data-service .
cd ..

echo "Building the App service"
cd app-provider-service
./mvnw clean package -DskipTests=true
docker build --tag app-service .
cd ..

#echo "Building the Mobile api gateway"
#cd mobile-api-gateway
#./mvnw clean package -DskipTests=true
#docker build --tag mobile-api-gateway .
#cd ..

echo "Building the forum service"
cd forum-service
./mvnw clean package -DskipTests=true
docker build --tag forum-service .
cd ..

echo "Building the scoreboard service"
cd scoreboard-service
./mvnw clean package -DskipTests=true
docker build --tag scoreboard-service .
cd ..

echo "Building dektop client"
cd desktop-client
docker build --tag desktop-client:latest .
cd ..