#!/bin/sh
set -e

# Wait for MySQL to be ready
/app/wait-for-it.sh mysql:3306 --timeout=60 --strict -- echo "MySQL is up"


echo "####### START ######"

# Start services in the background
java -jar /app/discovery-server.jar &
java -jar /app/api-gateway.jar &
java -jar /app/product-search-service.jar &
java -jar /app/product-setup-service.jar &

echo "####### EXECUTED ######"


# Wait for all background processes
wait
