#!/usr/bin/env bash

mvn clean install
read -p "Waiting 5 seconds...." -t 5
docker-compose up -d postgres
read -p "Waiting 15 seconds for postgres to start...." -t 15
docker-compose up -d cityapp
read -p "Waiting 15 seconds for backend to start...." -t 15
docker-compose up -d front-end
