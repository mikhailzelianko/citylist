#!/usr/bin/env bash

mvn clean install
read -p "Waiting 5 seconds...." -t 5
docker-compose up -d postgres
read -p "Waiting 5 seconds for postgres to start...." -t 5
docker-compose up -d --build cityapp
read -p "Waiting 5 seconds for backend to start...." -t 5
docker-compose up -d --build front-end
