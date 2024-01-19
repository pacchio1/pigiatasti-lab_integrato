#!/bin/bash
chmod -R 771 /app/*
chown -R $(whoami):$(whoami) /app/*
#nginx
service nginx start
#dump db
service mariadb start
mysql -u root -p'odiojava' -e "CREATE USER 'backend'@'%' IDENTIFIED BY 'odiojava'";
mysql -u root -p'odiojava' -e "GRANT ALL PRIVILEGES ON sevedemo.* TO 'backend'@'%'";
mysql -u root -p'odiojava' -e 'CREATE DATABASE sevedemo';
mysql -u root -p'odiojava' sevedemo < /db/sevedemo.sql
#start spring
cd /app/backend/SeVedemo
export JAVA_HOME=/usr/lib/jvm/java-1.17.0-openjdk-amd64
./mvnw clean install
./mvnw spring-boot:run
