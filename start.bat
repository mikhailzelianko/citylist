call mvn clean install
TIMEOUT /T 5
docker-compose up -d postgres
TIMEOUT /T 15
docker-compose up -d cityapp
TIMEOUT /T 15
docker-compose up -d front-end