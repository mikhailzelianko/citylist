call mvn clean install
TIMEOUT /T 5
docker-compose up -d postgres
TIMEOUT /T 5
docker-compose up -d --build cityapp
TIMEOUT /T 5
docker-compose up -d --build front-end